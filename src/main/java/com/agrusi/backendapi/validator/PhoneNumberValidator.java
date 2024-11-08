package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.validator.annotation.ValidPhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

        // Allow null (field not provided), but reject empty strings.
        // This is done because PATCH requests allow non-existent fields,
        // but we don't want to allow the consumer of the API to input empty
        // strings on other request types!

        if (phoneNumber == null) {
            return true; // Field not provided, skip validation
        }

        if (phoneNumber.isEmpty()) {
            return false; // Reject empty strings
        }

        try {
            // You can specify a default region if needed
            Phonenumber.PhoneNumber parsedPhone = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(parsedPhone); // Check if it's a valid number
        } catch (NumberParseException e) {
            return false;
        }
    }
}
