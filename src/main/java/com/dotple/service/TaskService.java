package com.dotple.service;

import com.dotple.domain.Task;
import com.dotple.domain.Todo;
import com.dotple.domain.User;
import com.dotple.dto.task.TaskDTO;
import com.dotple.dto.task.TodoDTO;
import com.dotple.repository.TaskRepository;
import com.dotple.repository.TodoRepository;
import com.dotple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

	private final UserRepository userRepository;
	private final TodoRepository todoRepository;
	private final TaskRepository taskRepository;

	public List<TaskDTO.Res> getRoot(String monthReq) {

		User user = userRepository.findById(1L).get();

		// 조회할 범위 획득
		int year = Integer.parseInt(monthReq.substring(0, 4));
		Month month = Month.of(Integer.parseInt(monthReq.substring(4)));
		// 해당하는 달의 첫째날
		LocalDate start = LocalDate.of(year, month, 1);
		// 첫째날의 요일 (0: SUN, 1: MON, ..., 6: SAT)
		int dayOfWeek = start.getDayOfWeek().getValue() % 7;
		// 해당하는 달의 마지막날
		LocalDate end = LocalDate.of(year, month, month.length(Year.isLeap(year)));

		List<TaskDTO.Res> res = new ArrayList<>();

		// monthReq와 기간이 겹치는 todo를 조회
		List<Todo> overlappedTodos = todoRepository.findOverlappedWith(user, start, end);
		// task 개수를 초기화
		List<Integer> taskCntInit = overlappedTodos.stream()
				.map(todo -> {
					if (todo.getIterType() == 1)
						return todo.getIterVal();	// 횟수를 계수할 용도
					else return 99;	// 횟수 todo가 아닌 것은 99로 초기화
				}).toList();
		List<Integer> taskCnt = new ArrayList<>(taskCntInit);
		// task를 날짜별로 저장
		List<List<Task>> tasksOfWeek = new ArrayList<>(7);

		for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1L)) {

			List<Task> tasksOfDay = new ArrayList<>();
			// 매 date에 대해, 각 todo에 속하는 task를 조회
			for (int i = 0; i < overlappedTodos.size(); i++) {

				Todo todo = overlappedTodos.get(i);

				Task task = taskRepository.findByTodoAndDate(todo, date);

				// 일주일 동안 특정 todo에 해당하는 task의 개수를 계수
				taskCnt.set(i, taskCnt.get(i) - 1);
				tasksOfDay.add(task);
			}
			tasksOfWeek.set(dayOfWeek, tasksOfDay);

			// 카테고리별 할 일 목록
			// if task.category in res.category
			//	then res.find(tasks.category).task <- task
			List<TodoDTO> todos = new ArrayList<>();

			// 일주일이 지났으면 res에 add할 데이터를 결정
			if (dayOfWeek++ == 6) {

				// 만약 task count가 목표값보다 작으면
				//	일주일에 대하여 tasksOfWeek을 순회
				//	 특정한 todo의 task가 없으면 state=0인 task를 삽입
				//	 각 task를 변환하여 res에 삽입
				// 만약 task count가 목표값을 달성했다면
				//	일주일에 대하여 tasksOfWeek을 순회
				//	 각 task를 변환하여 res에 삽입

				// overlappedTodos의 초반부는 그 주 전체에 대한 조회를 하지 않는다는 문제가 있음!!!

				// 완성된 날짜별 task를 res에 삽입
				res.add(TaskDTO.Res.builder()
						.day(date.getDayOfMonth())
						.todos(todos)
						.build());

			} else {
				dayOfWeek = 0;
				taskCnt = new ArrayList<>(taskCntInit);
				tasksOfWeek = new ArrayList<>(7);
			}
		}

		return res;
	}
}
