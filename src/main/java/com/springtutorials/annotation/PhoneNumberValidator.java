package com.springtutorials.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext context) {
		
		if(phoneField == null) {
			return false;
		}
		return phoneField.matches("^[6-9]\\d{9}$");
	}
	
	

}
