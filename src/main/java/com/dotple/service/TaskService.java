package com.dotple.service;

import com.dotple.controller.advice.CustomException;
import com.dotple.controller.advice.ResponseCode;
import com.dotple.domain.Task;
import com.dotple.domain.Todo;
import com.dotple.domain.User;
import com.dotple.dto.task.TaskDTO;
import com.dotple.dto.task.TodoDTO;
import com.dotple.repository.TaskRepository;
import com.dotple.repository.TodoRepository;
import com.dotple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

	private final UserRepository userRepository;
	private final TodoRepository todoRepository;
	private final TaskRepository taskRepository;

	public List<TaskDTO.Res> getRoot(YearMonth monthReq) {

		// TODO: 세션에서 사용자 정보 획득
		User user = userRepository.findById(1L).get();

		// 조회할 범위 획득
		int year = monthReq.getYear();
		Month month = monthReq.getMonth();
		// 해당하는 달의 첫째날
		LocalDate start = LocalDate.of(year, month, 1);
		// 해당하는 달의 마지막날
		LocalDate end = LocalDate.of(year, month, month.length(Year.isLeap(year)));

		List<TaskDTO.Res> res = new ArrayList<>();

		// monthReq와 기간이 겹치는 todo를 조회
		List<Todo> overlappedTodos = todoRepository.findOverlappedWith(user, start, end);

		for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1L)) {

			List<TodoDTO> todosOfDay = new ArrayList<>();
			// 해당 날짜의 task를 조회
			for (Todo todo: overlappedTodos) {

				Optional<Task> optionalTask = taskRepository.findByTodoAndDate(todo, date);
				if (optionalTask.isEmpty()) continue;
				Task task = optionalTask.get();

				// 숨김 상태에 있는 task는 넘어감
				if (task.getState().equals(0))
					continue;

				TaskDTO taskDTO = TaskDTO.builder()
						.name(todo.getName())
						.taskId(task.getId())
						.iterType(todo.getIterType())
						.state(task.getState())
						.alarm(todo.getAlarm() != null)
						.build();

				Optional<TodoDTO> OptionalTodo = todosOfDay.stream()
						.filter(t -> t.getCategory().equals(todo.getCategory().getName()))
						.findAny();
				if (OptionalTodo.isPresent()) {
					// 이미 존재하는 TodoDTO.Tasks:task list에 해당 task를 삽입
					OptionalTodo.get().getTasks().add(taskDTO);
				} else {
					// 새로운 task list를 만들어 TodoDTO를 생성 및 todosOfDay에 삽입
					todosOfDay.add(TodoDTO.builder()
							.category(todo.getCategory().getName())
							.color(todo.getCategory().getColor())
							.tasks(new ArrayList<>(List.of(taskDTO)))
							.build());
				}
			}

			// 하루치 todoList를 삽입
			res.add(TaskDTO.Res.builder()
					.day(date.getDayOfMonth())
					.todos(todosOfDay)
					.build());
		}

		return res;
	}

	public TaskDTO.State putRegistration(Long taskId) {

		User user = userRepository.findById(1L).get();

		// 변경하려는 task를 획득
		Task task = taskRepository.findById(taskId).get();

		// 변경하려는 task가 요청 user의 것인지 확인 (비정상 접근)
		if (task.getTodo().getCategory().getUser() != user)
			throw new CustomException(ResponseCode.C4040);

		// 횟수 todo가 맞는지 확인 (비정상 접근)
		Todo todo = task.getTodo();
		if (!todo.getIterType().equals(1))
			throw new CustomException(ResponseCode.C4091);

		// 목표 달성 여부 확인을 위해, 해당 task가 속한 일주일의 범위 획득
		LocalDate date = task.getDate();
		int day = date.getDayOfWeek().getValue() % 7;	// 0: SUN, 1: MON, ..., 6: SAT
		LocalDate start = date.minusDays(day);
		LocalDate end = date.plusDays(6 - day);

		// task 상태 변경
		switch (task.getState()) {

			// 미등록 상태라면, 등록 상태로 변경
			case 1 -> {
				// 일주일 내의 다른 task의 개수를 조회하여 목표 개수를 달성했는지 확인
				Long otherTaskCnt = taskRepository.countTasksOfSameTodo(todo, start, end);

				// 이미 목표가 달성 되어 있으면 추가적으로 등록할 수 없음 (비정상 접근)
				if (otherTaskCnt >= todo.getIterVal())
					throw new CustomException(ResponseCode.C4091);

				task.setState(2);

				// 변경으로 인해 목표가 달성되었다면, 다른 task 중 미등록 상태인 것들을 숨김 상태로 변경
				if (otherTaskCnt + 1 == todo.getIterVal()) {
					int modifiedRows = taskRepository.updateStateByTodoAndStateAndDateBetween(0, todo, 1, start, end);
					log.info("{} rows are turned from '1' to '0'", modifiedRows);
				}
			}

			// 등록 상태라면, 미등록 상태로 변경
			case 2 -> {

				task.setState(1);

				// 해당 주에 숨김 상태인 task가 있다면 미등록 상태로 변경
				// (조건에 해당하지 않으면 변경 값도 없을 것이므로, 조건을 확인하지 않기로 함)
				int modifiedRows = taskRepository.updateStateByTodoAndStateAndDateBetween(1, todo, 0, start, end);
				log.info("{} rows are turned from '0' to '1'", modifiedRows);
			}

			// 상태 변경 조건에 부합하지 않으면 변경하지 않음
			default -> throw new CustomException(ResponseCode.C4091);
		}
		taskRepository.save(task);

		return TaskDTO.State.builder()
				.state(task.getState())
				.build();
	}

	public TaskDTO.State putPerformance(Long taskId) {

		User user = userRepository.findById(1L).get();

		// 변경하려는 task를 획득
		Task task = taskRepository.findById(taskId).get();

		// 변경하려는 task가 요청 user의 것인지 확인 (비정상 접근)
		if (task.getTodo().getCategory().getUser() != user)
			throw new CustomException(ResponseCode.C4040);

		// task 상태 변경
		switch (task.getState()) {

			// 미수행 상태라면, 수행 상태로 변경
			case 2 -> task.setState(3);

			// 수행 상태라면, 미수행 상태로 변경
			case 3 -> task.setState(2);

			// 상태 변경 조건에 부합하지 않으면 변경하지 않음
			default -> throw new CustomException(ResponseCode.C4091);
		}
		taskRepository.save(task);

		return TaskDTO.State.builder()
				.state(task.getState())
				.build();
	}
}
