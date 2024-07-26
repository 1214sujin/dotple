package com.dotple.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

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
	public static class Req {

		@NotNull
		private Long todoId;
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
		private String date;
	}

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
