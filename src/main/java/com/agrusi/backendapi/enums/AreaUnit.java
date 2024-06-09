package com.agrusi.backendapi.enums;

public enum AreaUnit {
    HECTARE("hectare"),
    SQUARE_METER("square metre"),
    ACRE("acre");

    private final String unitOfArea;

    AreaUnit(String unitOfArea) {
        this.unitOfArea = unitOfArea;
    }

    public String getUnitOfArea() {
        return unitOfArea;
    }
}
