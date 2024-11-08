package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.validator.annotation.ValidTimeZone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZoneId;

public class TimeZoneValidator implements ConstraintValidator<ValidTimeZone, String> {

    @Override
    public boolean isValid(String timeZone, ConstraintValidatorContext context) {

        // Allow null (field not provided), but reject empty strings.
        // This is done because PATCH requests allow non-existent fields,
        // but we don't want to allow the consumer of the API to input empty
        // strings on other request types!

        if (timeZone == null) { // Field not provided, skip validation
            return true;
        }

        if (timeZone.isEmpty()) {
            return false; // Reject empty strings
        }

        // Validate if the provided time zone string is valid according to ZoneId,
        // which specifies a time zone identifier and provides rules for converting
        // between an Instant and a LocalDateTime in Java

        return ZoneId.getAvailableZoneIds().contains(timeZone);
    }
}

