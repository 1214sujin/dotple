package com.dotple.repository;

import com.dotple.domain.Task;
import com.dotple.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findByTodoAndDate(Todo todo, LocalDate date);

	// 요청된 날짜가 속한 주의 동일 목표 task 개수 조회
	Long countByTodoAndStateGreaterThanAndDateBetween(Todo todo, Integer state, LocalDate start, LocalDate end);
	default Long countTasksOfSameTodo(Todo todo, LocalDate start, LocalDate end) {
		return countByTodoAndStateGreaterThanAndDateBetween(todo, 1, start, end);
	}

	// 요청된 범위의 task.state 값을 일괄 변경
	@Modifying
	@Query("update Task t set t.state = :newState where t.todo = :todo " +
			"and t.state = :state and t.date between :start and :end")
	int updateStateByTodoAndStateAndDateBetween(@Param("newState") Integer newState, @Param("todo") Todo todo,
						@Param("state") Integer state, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
