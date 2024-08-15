package com.dotple.dto.task;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaskDTO {

	private String name;
	private Long taskId;
	private Integer iterType;
	private Integer state;
	private Boolean alarm;

	@Getter
	@Builder
	public static class Res {

		private Integer day;
		private List<TodoDTO> todos;
	}

	@Getter
	@Builder
	public static class State {

		private Integer state;
	}
}
