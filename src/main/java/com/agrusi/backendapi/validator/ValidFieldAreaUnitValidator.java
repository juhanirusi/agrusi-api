package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.validator.annotation.ValidFieldAreaUnit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFieldAreaUnitValidator implements
        ConstraintValidator<ValidFieldAreaUnit, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Allow null (field not provided), but reject empty strings.
        // This is done because PATCH requests allow non-existent fields,
        // but we don't want to allow the consumer of the API to input empty
        // strings on other request types!

        if (value == null) { // Field not provided, skip validation
            return true;
        }

        for (EAreaUnit areaUnit : EAreaUnit.values()) {

            if (areaUnit.getUnitOfArea().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
