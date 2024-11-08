package com.agrusi.backendapi.dto.request.farm;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FarmPatchDto {

    @Size(
            min = 2,
            max = 255,
            message = "Farm name needs to be " +
                    "between {min} and {max} characters long."
    )
    private String farmName;

    public FarmPatchDto() {
    }

    public FarmPatchDto(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmName() {
        return farmName;
    }

    // Trim the "farmName" if it's NOT null, otherwise leave it null

    public void setFarmName(String farmName) {
        this.farmName = (farmName != null) ? farmName.trim() : null;
    }
}
