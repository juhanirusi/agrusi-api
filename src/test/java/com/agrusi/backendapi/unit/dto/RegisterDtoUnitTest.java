package com.agrusi.backendapi.unit.dto;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@UnitTest
public class RegisterDtoUnitTest {

    private Validator validator;

    private String validFirstName;
    private String validLastName;
    private String validEmail;
    private String validPassword;
    private String validLanguageCode;
    private String validCurrencyCode;
    private String validTimeZone;

    private EAreaUnit validFieldAreaUnitHectare;
    private EAreaUnit validFieldAreaUnitSquareMetre;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validFirstName = "Jack";
        validLastName = "Farmer";
        validEmail = "jack.farmer@example.com";
        validPassword = "StrongPassword123@";
        validLanguageCode = "fi";
        validCurrencyCode = "EUR";
        validTimeZone = "Europe/Helsinki";

        validFieldAreaUnitHectare = EAreaUnit.HECTARE;
        validFieldAreaUnitSquareMetre = EAreaUnit.SQUARE_METRE;
    }

    @Test
    @DisplayName("Test that when all fields are valid we get no violations.")
    public void testAllFieldsAreValid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                validPassword,
                validLanguageCode,
                validCurrencyCode,
                validTimeZone,
                validFieldAreaUnitHectare.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Test that invalid email address throws a violation.")
    public void testEmailIsInvalidConstraintViolation() {
        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                "invalid-email",
                validPassword,
                validLanguageCode,
                validCurrencyCode,
                validTimeZone,
                validFieldAreaUnitHectare.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Please provide a valid email address.");
    }

    @Test
    @DisplayName("Test that invalid password throws a violation.")
    public void testPasswordIsInvalid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                "weak",
                validLanguageCode,
                validCurrencyCode,
                validTimeZone,
                validFieldAreaUnitSquareMetre.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
        assertThat(violation.getMessage()).contains(
                "Password must be 8 or more characters in length. " +
                        "Password must contain 1 or more uppercase characters. " +
                        "Password must contain 1 or more digit characters."
        );
    }

    @Test
    @DisplayName("Test that invalid language code throws a violation.")
    public void testLanguageCodeIsInvalid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                validPassword,
                "fiiiiiiiii",
                validCurrencyCode,
                validTimeZone,
                validFieldAreaUnitSquareMetre.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("language");
        assertThat(violation.getMessage()).isEqualTo("Invalid ISO 639 language code.");
    }

    @Test
    @DisplayName("Test that invalid currency code throws a violation.")
    public void testCurrencyCodeIsInvalid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                validPassword,
                validLanguageCode,
                "EURO",
                validTimeZone,
                validFieldAreaUnitHectare.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("currency");
        assertThat(violation.getMessage()).isEqualTo("Invalid ISO 4217 currency code.");
    }

    @Test
    @DisplayName("Test that invalid time zone throws a violation.")
    public void testTimeZoneIsInvalid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                validPassword,
                validLanguageCode,
                validCurrencyCode,
                "Erope/Helinki",
                validFieldAreaUnitHectare.getUnitOfArea()
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("timeZone");
        assertThat(violation.getMessage()).isEqualTo("Invalid tz database time zone.");
    }

    @Test
    @DisplayName("Test that invalid field area unit throws a violation.")
    public void testFieldAreaUnitIsInvalid() {

        RegisterDto registerDto = new RegisterDto(
                validFirstName,
                validLastName,
                validEmail,
                validPassword,
                validLanguageCode,
                validCurrencyCode,
                validTimeZone,
                "metric"
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<RegisterDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("fieldAreaUnit");
        assertThat(violation.getMessage()).isEqualTo("Invalid area unit.");
    }
}
