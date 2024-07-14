package com.agrusi.backendapi.enums;

public enum EAreaUnit {
    HECTARE("hectare"),
    SQUARE_METER("square metre"),
    ACRE("acre");

    private final String unitOfArea;

    EAreaUnit(String unitOfArea) {
        this.unitOfArea = unitOfArea;
    }

    public String getUnitOfArea() {
        return unitOfArea;
    }
}
