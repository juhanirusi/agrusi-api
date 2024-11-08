package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default "Please provide a valid phone number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
