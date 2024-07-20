package com.dotple.controller;

import com.dotple.controller.advice.ResponseMessage;
import com.dotple.dto.task.TaskDTO;
import com.dotple.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@GetMapping("/{month}")
	public ResponseEntity<ResponseMessage<List<TaskDTO.Res>>> getRoot(@PathVariable("month")
																	  @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
		List<TaskDTO.Res> res = taskService.getRoot(month);

		return ResponseEntity.ok(new ResponseMessage<>(res));
	}
}
