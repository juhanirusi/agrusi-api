package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.CurrencyCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrencyCode {

    String message() default "Invalid ISO 4217 currency code.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
