package com.agrusi.backendapi.enums;

public enum EAddressType {

    HOME("home"),
    FARM_MAIN("farm main"),
    SHIPPING("shipping"),
    STORAGE_FACILITY("storage facility"),
    GRAIN_STORAGE("grain storage");

    private final String addressType;

    EAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getFarmAddressType() {
        return addressType;
    }
}
