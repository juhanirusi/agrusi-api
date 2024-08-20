package com.agrusi.backendapi.dto.request.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FarmPostDto {

    @NotBlank(message = "Farm name is mandatory.")
    @NotEmpty(message = "Farm name can't be empty.")
    @Size(
            min = 2,
            max = 255,
            message = "Farm name is required and needs to be " +
                    "between {min} and {max} characters long."
    )
    private String farmName;

    public FarmPostDto() {
    }

    public FarmPostDto(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
}
