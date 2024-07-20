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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

	private final UserRepository userRepository;
	private final TodoRepository todoRepository;
	private final TaskRepository taskRepository;

	public List<TaskDTO.Res> getRoot(YearMonth monthReq) {

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
				if (task.getState().equals(3))
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
}
