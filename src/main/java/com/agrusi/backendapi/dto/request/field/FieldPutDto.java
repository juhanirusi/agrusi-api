package com.agrusi.backendapi.dto.request.field;

import com.agrusi.backendapi.validator.annotation.ValidCoordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class FieldPutDto {

    @NotBlank(message = "Field name is mandatory.")
    @NotEmpty(message = "Field name can't be empty.")
    @Size(
            min = 2,
            max = 255,
            message = "Field name is required and needs to be " +
                    "between {min} and {max} characters long.."
    )
    private String name;

    @ValidCoordinates
    private List<List<List<Double>>> coordinates;

    public FieldPutDto(
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
