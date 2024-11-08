package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.LanguageCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LanguageCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLanguageCode {

    String message() default "Invalid ISO 639 language code.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
