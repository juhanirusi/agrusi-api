package com.agrusi.backendapi.dto.request.account;

import com.agrusi.backendapi.validator.annotation.ValidEmail;
import com.agrusi.backendapi.validator.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.Size;

public class AccountPatchGeneralDto {

    // Allow nulls for PATCH, validate only if the field is provided (non-null)

    @Size(
            min = 2,
            max = 255,
            message = "First name is required and needs to be " +
                    "between {min} and {max} characters long."
    )
    private String firstName;

    // Allow nulls for PATCH, validate only if the field is provided (non-null)

    @Size(
            min = 2,
            max = 255,
            message = "Last name is required and needs to be " +
                    "between {min} and {max} characters long."
    )
    private String lastName;

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;

    public AccountPatchGeneralDto() {
    }

    public AccountPatchGeneralDto(
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
