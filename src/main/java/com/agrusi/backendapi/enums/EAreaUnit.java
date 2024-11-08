package com.agrusi.backendapi.enums;

public enum EAreaUnit {

    HECTARE("hectare"),
    SQUARE_METRE("square metre");

    private final String unitOfArea;

    EAreaUnit(String unitOfArea) {
        this.unitOfArea = unitOfArea;
    }

    public String getUnitOfArea() {
        return unitOfArea;
    }
}
