package com.dotple.repository;

import com.dotple.domain.Task;
import com.dotple.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findByTodoAndDate(Todo todo, LocalDate date);
}
