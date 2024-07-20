package com.dotple.dto.task;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TodoDTO {

	private String category;
	private String color;
	private List<TaskDTO> tasks;
}
