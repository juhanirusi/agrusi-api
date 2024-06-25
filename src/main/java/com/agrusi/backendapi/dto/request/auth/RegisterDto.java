package com.agrusi.backendapi.dto.request.auth;


import com.agrusi.backendapi.validator.annotation.ValidEmail;
import com.agrusi.backendapi.validator.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto {

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

    @NotBlank(message = "Email is mandatory.")
    @NotEmpty(message = "Email can't be empty.")
    @ValidEmail
    private String email;

    @NotBlank(message = "Password is mandatory.")
    @NotEmpty(message = "Password can't be empty.")
    @ValidPassword
    private String password;

    public RegisterDto() {
    }

    public RegisterDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
