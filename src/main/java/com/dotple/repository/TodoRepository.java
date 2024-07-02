package com.dotple.repository;

import com.dotple.domain.Category;
import com.dotple.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	Boolean existsByCategoryAndEndGreaterThanEqual(Category category, LocalDate end);
}
