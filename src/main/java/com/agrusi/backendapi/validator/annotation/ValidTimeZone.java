package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.TimeZoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeZoneValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeZone {

    String message() default "Invalid tz database time zone.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
