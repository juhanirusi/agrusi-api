package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.util.ISOCodeUtil;
import com.agrusi.backendapi.validator.annotation.ValidCurrencyCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {

        // Allow null (field not provided), but reject empty strings.
        // This is done because PATCH requests allow non-existent fields,
        // but we don't want to allow the consumer of the API to input empty
        // strings on other request types!

        if (currencyCode == null) { // Field not provided, skip validation
            return true;
        }

        if (currencyCode.isEmpty()) {
            return false; // Reject empty strings
        }

        return ISOCodeUtil.isValidCurrencyCode(currencyCode);
    }
}
