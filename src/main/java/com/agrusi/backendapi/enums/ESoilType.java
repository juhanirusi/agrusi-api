package com.agrusi.backendapi.enums;

public enum ESoilType {

    LOAM("loam"),
    SANDY("sandy"),
    CLAY("clay"),
    SILT("silt"),
    PEATY("peaty"),
    CHALKY("chalky"),
    SALINE("saline"),
    SANDY_LOAM("sandy loam"),
    SILT_LOAM("silt loam"),
    CLAY_LOAM("clay loam");

    private final String soilType;

    ESoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getSoilType() {
        return soilType;
    }
}
