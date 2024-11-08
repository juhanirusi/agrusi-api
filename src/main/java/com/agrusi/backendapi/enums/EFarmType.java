package com.agrusi.backendapi.enums;

public enum EFarmType {

    CROP("crop"),
    HORTICULTURE("horticulture"),
    DAIRY("dairy"),
    POULTRY("poultry"),
    LIVESTOCK("livestock");

    private final String farmType;

    EFarmType(String farmType) {
        this.farmType = farmType;
    }

    public String getFarmType() {
        return farmType;
    }
}
