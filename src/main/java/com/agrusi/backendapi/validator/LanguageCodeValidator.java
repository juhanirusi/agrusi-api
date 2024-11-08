package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.util.ISOCodeUtil;
import com.agrusi.backendapi.validator.annotation.ValidLanguageCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LanguageCodeValidator implements ConstraintValidator<ValidLanguageCode, String> {

    @Override
    public boolean isValid(String languageCode, ConstraintValidatorContext context) {

        // Allow null (field not provided), but reject empty strings.
        // This is done because PATCH requests allow non-existent fields,
        // but we don't want to allow the consumer of the API to input empty
        // strings on other request types!

        if (languageCode == null) { // Field not provided, skip validation
            return true;
        }

        if (languageCode.isEmpty()) {
            return false; // Reject empty strings
        }

        return ISOCodeUtil.isValidLanguageCode(languageCode);
    }
}
