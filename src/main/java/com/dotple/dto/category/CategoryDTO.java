package com.dotple.dto.category;

import com.dotple.controller.advice.HexCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CategoryDTO {

	@Getter
	@Setter
	public static class Req {

		@NotBlank
		private String name;
		@HexCode
		private String color;
	}

	@Getter
	@Builder
	public static class Res {

		private String name;
		private Long categoryId;
		private String color;
	}

	@Getter
	@AllArgsConstructor
	public static class Check {

		private Boolean deletable;
	}
}
