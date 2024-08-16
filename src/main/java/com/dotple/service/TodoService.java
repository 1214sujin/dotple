package com.dotple.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dotple.controller.advice.CustomException;
import com.dotple.controller.advice.ResponseCode;
import com.dotple.domain.Category;
import com.dotple.domain.Task;
import com.dotple.domain.Todo;
import com.dotple.domain.User;
import com.dotple.dto.todo.TodoDTO;
import com.dotple.repository.CategoryRepository;
import com.dotple.repository.TaskRepository;
import com.dotple.repository.TodoRepository;
import com.dotple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final TodoRepository todoRepository;
	private final TaskRepository taskRepository;

	public void postRoot(TodoDTO.Create create) {

		// TODO: 세션에서 사용자 정보 획득
		User user = userRepository.findById(1L).get();

		// req 분해
		Long categoryId = create.getCategoryId();
		String name = create.getName();
		LocalDate start = create.getStart();
		LocalDate end = create.getEnd();
		Integer iterType = create.getIterType();
		Integer iterVal = create.getIterVal();

		// 요청 유효성 검사
		// 카테고리 존재 여부
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		if (categoryOptional.isEmpty() || !user.equals(categoryOptional.get().getUser()))
			throw new CustomException(ResponseCode.C4040);
		// start end 순서
		if (!end.isAfter(start) || !List.of(1, 2).contains(iterType))
			throw new CustomException(ResponseCode.C4001);
		// iterType, iterVal 하루 이상 매일 이하
		if (!(iterType.equals(1) && 0 < iterVal && iterVal < 8
				|| iterType.equals(2) && 0 < iterVal && iterVal < 128))
			throw new CustomException(ResponseCode.C4001);
		// 겹치는 시기에 동일한 이름의 todo가 존재하는지 검사
		if (todoRepository.existsOverlappedWithByName(user, start, end, name))
			throw new CustomException(ResponseCode.C4090);

		// 새 todo 생성
		Todo todo = new Todo();
		todo.setCategory(categoryOptional.get());
		todo.setName(name);
		todo.setStart(start);
		todo.setEnd(end);
		todo.setIterType(iterType);
		todo.setIterVal(iterVal);
		todo.setAlarm(null);
		todoRepository.save(todo);

		// 해당하는 기간의 task 생성
		if (iterType.equals(1)) {	// 횟수 할 일 -> 기간 중 모든 날짜에 대하여 생성

			for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
				// 새 task 생성
				Task task = new Task();
				task.setTodo(todo);
				task.setDate(date);
				task.setState(1);	// 미등록
				taskRepository.save(task);
				log.debug("new task of '{}' is created on {}", name, date.toString());
			}
		} else {					// 요일 할 일 -> 기간 중 특정 요일에 대하여 생성

			// 목표에 해당하는 요일 리스트
			// iterVal (i):	7 6 5 4 3 2 1
			// DayOfWeek:	7 1 2 3 4 5 6 = 7 - i % 7
			List<DayOfWeek> dayList = new ArrayList<>();
			for (int i = 1; i <= 7; i++) {
				if (iterVal % 2 == 1) {
					dayList.add(DayOfWeek.of(7 - i % 7));
					log.debug("day of week: {}", DayOfWeek.of(7 - i % 7));
				}
				iterVal >>= 1;
			}

			// 주별로 for문을 돌리기 위해, start와 end의 주를 계산
			// 0: SUN, 1: MON, ..., 6: SAT
			LocalDate startWeek = start.minusDays(start.getDayOfWeek().getValue() % 7);
			LocalDate endWeek = end.minusDays(end.getDayOfWeek().getValue() % 7);

			for (LocalDate date = startWeek; date.isBefore(endWeek); date = date.plusDays(7)) {

				for (DayOfWeek day : dayList) {

					LocalDate taskDate = date.plusDays(day.getValue() % 7);
					// start와 end 사이인지 한 번 더 확인
					if (!taskDate.isBefore(start) && !taskDate.isAfter(end)) {
						// 새 task 생성
						Task task = new Task();
						task.setTodo(todo);
						task.setDate(taskDate);
						task.setState(2);	// 미수행
						taskRepository.save(task);
						log.debug("new task of '{}' is created on {}", name, taskDate.toString());
					}
				}
			}
		}
	}
}
