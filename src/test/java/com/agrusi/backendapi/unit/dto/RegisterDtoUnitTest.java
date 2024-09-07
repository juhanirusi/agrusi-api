package com.agrusi.backendapi.unit.dto;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
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

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test that when all fields are valid we get no violations.")
    public void testAllFieldsAreValid() {
        RegisterDto registerDto = new RegisterDto(
                "Jack",
                "Farmer",
                "jack.farmer@example.com",
                "StrongP@ssword123"
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Test that invalid email address throws a violation.")
    public void testEmailIsInvalidConstraintViolation() {
        RegisterDto registerDto = new RegisterDto(
                "John",
                "Doe",
                "invalid-email",
                "StrongP@ssword123"
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
                "Jack",
                "Farmer",
                "jack.farmer@example.com",
                "weak"
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
}
