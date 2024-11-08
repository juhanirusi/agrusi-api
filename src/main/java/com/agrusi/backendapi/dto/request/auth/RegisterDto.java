package com.agrusi.backendapi.dto.request.auth;

import com.agrusi.backendapi.validator.annotation.*;
import jakarta.validation.constraints.NotBlank;
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
    @ValidEmail
    private String email;

    @NotBlank(message = "Password is mandatory.")
    @ValidPassword
    private String password;

    @NotBlank(message = "Please provide a valid ISO 639 language code.")
    @ValidLanguageCode
    private String language; // e.g., "fi", "en-us"

    @NotBlank(message = "Please provide a valid ISO 4217 currency code.")
    @ValidCurrencyCode
    private String currency; // e.g., "EUR", "USD"

    @NotBlank(message = "Please provide a valid tz database time zone.")
    @ValidTimeZone
    private String timeZone; // e.g., "Europe/Helsinki", "America/New_York"

    @NotBlank(message = "Please provide a preferred field area unit.")
    @ValidFieldAreaUnit
    private String fieldAreaUnit; // e.g., "hectare", "acre"

    public RegisterDto() {
    }

    public RegisterDto(
            String firstName,
            String lastName,
            String email,
            String password,
            String language,
            String currency,
            String timeZone,
            String fieldAreaUnit
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.language = language;
        this.currency = currency;
        this.timeZone = timeZone;
        this.fieldAreaUnit = fieldAreaUnit;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language.toLowerCase();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency.toUpperCase();
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getFieldAreaUnit() {
        return fieldAreaUnit;
    }

    public void setFieldAreaUnit(String fieldAreaUnit) {
        this.fieldAreaUnit = fieldAreaUnit;
    }
}
