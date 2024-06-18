package com.agrusi.backendapi.exception.farm;

import java.util.UUID;

public class FarmNotFoundException extends RuntimeException {

    public FarmNotFoundException(UUID publicFarmId) {
        super("Farm with public ID '" + publicFarmId + "' not found.");
    }

    public FarmNotFoundException(String message) {
        super(message);
    }
}
