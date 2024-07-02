package com.dotple.service;

import com.dotple.controller.advice.CustomException;
import com.dotple.controller.advice.ResponseCode;
import com.dotple.domain.Category;
import com.dotple.domain.User;
import com.dotple.dto.category.CategoryDTO.*;
import com.dotple.repository.CategoryRepository;
import com.dotple.repository.TodoRepository;
import com.dotple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final TodoRepository todoRepository;

	public List<Res> getRoot() {

		User user = userRepository.findById(1L).get();

		// 사용자의 카테고리 목록 조회
		List<Category> categories = categoryRepository.findByUser(user);

		return categories.stream().map(
				category -> Res.builder()
						.name(category.getName())
						.categoryId(category.getId())
						.color(category.getColor())
						.build()
		).toList();
	}

	public Long postRoot(Req req) {

		User user = userRepository.findById(1L).get();

		// 새 카테고리 생성
		Category category = new Category();
		category.setUser(user);
		category.setName(req.getName());
		category.setColor(req.getColor());

		return categoryRepository.save(category).getId();
	}

	public Long putRoot(Long categoryId, Req req) {

		User user = userRepository.findById(1L).get();

		Category category = categoryRepository.findById(categoryId).get();

		// 카테고리 수정
		category.setUser(user);
		category.setName(req.getName());
		category.setColor(req.getColor());

		return categoryRepository.save(category).getId();
	}

	public Check getDeletable(Long categoryId) {

		User user = userRepository.findById(1L).get();

		Category category = categoryRepository.findById(categoryId).get();
		// 사용자의 카테고리가 맞는지 검사
		if (!category.getUser().equals(user)) throw new CustomException(ResponseCode.C4040);

		// 사용자가 삭제하고 싶은 카테고리 하위의 목표 중, 종료일이 오늘이거나 그 후인 것이 있으면 ~deletable
		LocalDate today = LocalDate.now();
//		log.debug(today);
		if (todoRepository.existsByCategoryAndEndGreaterThanEqual(category, today))
			return new Check(false);
		return new Check(true);
	}

	public void deleteRoot(Long categoryId) {

		User user = userRepository.findById(1L).get();

		Category category = categoryRepository.findById(categoryId).get();
		// 사용자의 카테고리가 맞는지 검사
		if (!category.getUser().equals(user)) throw new CustomException(ResponseCode.C4040);

		// 카테고리 삭제
		categoryRepository.delete(category);
	}
}
