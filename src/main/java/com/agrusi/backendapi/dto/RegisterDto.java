package com.agrusi.backendapi.dto;


import com.agrusi.backendapi.validator.annotation.ValidEmail;
import com.agrusi.backendapi.validator.annotation.ValidPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;

public class RegisterDto {

    @ValidEmail
    @NotEmpty(message = "Email can't be empty")
    //@Email(message = "Please provide a valid email address")
    private String email;

    //@Size(min = 8, message = "Password needs to be at least 8 characters")
    @ValidPassword
    private String password;

    private String confirmPassword;

    @AssertTrue(message = "Passwords don't match")
    public boolean isConfirmPassword() {
        return password.equals(confirmPassword);
    }

    public RegisterDto() {
    }

    public RegisterDto(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
