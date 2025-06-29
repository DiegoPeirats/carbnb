package com.example.demo.application.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
	
	String message() default "Password must be at least 8 characters long and contain a mix of uppercase, lowercase and number";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
