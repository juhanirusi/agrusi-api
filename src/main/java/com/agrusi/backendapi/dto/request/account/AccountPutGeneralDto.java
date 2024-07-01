package com.agrusi.backendapi.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountPutGeneralDto {

    @NotBlank(message = "First name is mandatory.")
    @Size(
            min = 2,
            max = 255,
            message = "First name is required and needs to be " +
                    "between {min} and {max} characters long."
    )
    private String firstName;

    @NotBlank(message = "Last name is mandatory.")
    @Size(
            min = 2,
            max = 255,
            message = "Last name is required and needs to be " +
                    "between {min} and {max} characters long."
    )
    private String lastName;

    public AccountPutGeneralDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
