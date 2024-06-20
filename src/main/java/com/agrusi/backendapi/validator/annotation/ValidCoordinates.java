package com.agrusi.backendapi.validator.annotation;

import com.agrusi.backendapi.validator.CoordinatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({
        ElementType.METHOD, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE, ElementType.PARAMETER
})
@Constraint(validatedBy = CoordinatesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoordinates {

    String message() default "Invalid coordinates format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
