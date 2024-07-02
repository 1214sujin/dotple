package com.dotple.controller.advice;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexCodeValidator implements ConstraintValidator<HexCode, String> {

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

		if (valueForValidation == null) return false;

		if (valueForValidation.length() == 8 && valueForValidation.matches("[\\dA-Za-z]{8}"))
			return true;

		throw new CustomException(ResponseCode.C4001);
	}
}
