package com.agrusi.backendapi.dto.request;

import java.util.List;

public class FieldDto {

    private String name;
    private List<List<List<Double>>> coordinates;

    public FieldDto(
            String name,
            List<List<List<Double>>> coordinates
    ) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
