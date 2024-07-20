package com.dotple.repository;

import com.dotple.domain.Category;
import com.dotple.domain.Todo;
import com.dotple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	// 요청된 카테고리에 속하면서 종료일이 오늘이거나 그 후인 목표 존재 여부
	Boolean existsByCategoryAndEndGreaterThanEqual(Category category, LocalDate end);

	// 요청된 기간과 겹치는 목표 조회
	List<Todo> findByCategory_UserAndStartLessThanEqualAndEndLessThanEqual(User user, LocalDate start, LocalDate end);
	default List<Todo> findOverlappedWith(User user, LocalDate start, LocalDate end) {
		// 시작일이나 종료일 중 하루라도 겹치면 되므로, start와 end를 cross
		return findByCategory_UserAndStartLessThanEqualAndEndLessThanEqual(user, end, start);
	}
}
