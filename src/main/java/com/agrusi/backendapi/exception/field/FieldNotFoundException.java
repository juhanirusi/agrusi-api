package com.agrusi.backendapi.exception.field;

import com.agrusi.backendapi.exception.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FieldNotFoundException extends BaseCustomException {

    public FieldNotFoundException(Long fieldId, UUID publicFarmId) {
        super(
                HttpStatus.NOT_FOUND,
                "Field not found.",
                "Field not found with ID '"
                + fieldId + "' for farm with public ID '"
                + publicFarmId + "'."
        );
    }

    public FieldNotFoundException(Long fieldId) {
        super(
                HttpStatus.NOT_FOUND,
                "Field not found.",
                "Field with the provided field ID '" + fieldId + "' not found."
        );
    }

    public FieldNotFoundException() {
        super(
                HttpStatus.NOT_FOUND,
                "Field not found.",
                "Field with the provided ID not found."
        );
    }
}
