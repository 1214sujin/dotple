package com.dotple.dto.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class TodoDTO {

	@Getter
	@Builder
	public static class Create {

		@NotNull
		private Long categoryId;
		@NotBlank
		private String name;
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
		private LocalDate start;
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
		private LocalDate end;
		@NotNull
		private Integer iterType;
		@NotNull
		private Integer iterVal;
	}
}
