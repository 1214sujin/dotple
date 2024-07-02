package com.dotple.controller;

import com.dotple.controller.advice.ResponseMessage;
import com.dotple.dto.category.CategoryDTO.*;
import com.dotple.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping({"", "/"})
	public ResponseEntity<ResponseMessage<List<Res>>> getRoot() {

		List<Res> res = categoryService.getRoot();

		return ResponseEntity.ok(new ResponseMessage<>(res));
	}

	@PostMapping({"", "/"})
	public ResponseEntity<ResponseMessage<Long>> postRoot(@Valid Req req) {

		Long res = categoryService.postRoot(req);

		return ResponseEntity.ok(new ResponseMessage<>(res));
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<ResponseMessage<Long>> putRoot(@PathVariable("categoryId") Long categoryId, @Valid Req req) {

		Long res = categoryService.putRoot(categoryId, req);

		return ResponseEntity.ok(new ResponseMessage<>(res));
	}

	@GetMapping("/deletable/{categoryId}")
	public ResponseEntity<ResponseMessage<Check>> getDeletable(@PathVariable("categoryId") Long categoryId) {

		Check res = categoryService.getDeletable(categoryId);

		return ResponseEntity.ok(new ResponseMessage<>(res));
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ResponseMessage<String>> deleteRoot(@PathVariable("categoryId") Long categoryId) {

		categoryService.deleteRoot(categoryId);

		return ResponseEntity.ok(new ResponseMessage<>("요청 처리에 성공했습니다."));
	}
}
