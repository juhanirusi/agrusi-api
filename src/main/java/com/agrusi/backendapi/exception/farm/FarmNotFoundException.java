package com.agrusi.backendapi.exception.farm;

import com.agrusi.backendapi.exception.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FarmNotFoundException extends BaseCustomException {

    public FarmNotFoundException(UUID publicFarmId) {
        super(
                HttpStatus.NOT_FOUND,
                "Farm not found.",
                "Farm with public ID '" + publicFarmId + "' not found."
        );
    }

    public FarmNotFoundException() {
        super(
                HttpStatus.NOT_FOUND,
                "Farm not found.",
                "Farm with the provided public ID not found."
        );
    }
}
