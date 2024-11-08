package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.ValidFieldAreaUnitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidFieldAreaUnitValidator.class)
public @interface ValidFieldAreaUnit {

    String message() default "Invalid area unit.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
