package com.dotple.controller.advice;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexCodeValidator implements ConstraintValidator<HexCode, String> {

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

		return !(valueForValidation.length() == 7 && valueForValidation.matches("#[\\dA-Z]{6}"));
	}
}
