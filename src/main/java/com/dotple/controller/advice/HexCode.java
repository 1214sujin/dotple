package com.dotple.controller.advice;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {HexCodeValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface HexCode {
	String message() default "HexCode 규칙에 맞춘 문자열만 사용할 수 있습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
