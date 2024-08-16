package com.dotple.controller;

import com.dotple.controller.advice.ResponseMessage;
import com.dotple.dto.todo.TodoDTO;
import com.dotple.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping({"", "/"})
	public ResponseEntity<ResponseMessage<String>> postRoot(@RequestBody TodoDTO.Create create) {

		todoService.postRoot(create);

		return ResponseEntity.ok(new ResponseMessage<>("요청 처리에 성공했습니다."));
	}
}
