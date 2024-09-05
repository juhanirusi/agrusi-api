package com.agrusi.backendapi.unit.dto;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.field.FieldPostDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@UnitTest
public class FieldPostDtoUnitTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Create new field successfully.")
    public void testFieldPostDtoIsValidNoViolations() {

        List<List<Double>> polygon = new ArrayList<>();

        polygon.add(List.of(0.0, 0.0));
        polygon.add(List.of(0.0, 1.0));
        polygon.add(List.of(1.0, 1.0));
        polygon.add(List.of(0.0, 0.0)); // The closing point of a polygon needs to be present.

        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(polygon);

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", coordinates);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Try creating field with null coordinates.")
    void testWhenCoordinatesAreNull_thenConstraintViolation() {

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", null);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<FieldPostDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("coordinates");
        assertThat(violation.getMessage()).isEqualTo("Coordinates can't be empty.");
    }

    @Test
    @DisplayName("Try creating field with less coordinates points than needed.")
    public void whenPolygonHasLessThanFourPoints_thenConstraintViolation() {

        List<List<Double>> polygon = new ArrayList<>();

        // Not enough coordinate points to create a Polygon...

        polygon.add(List.of(0.0, 0.0));
        polygon.add(List.of(0.0, 1.0));
        polygon.add(List.of(0.0, 0.0));

        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(polygon);

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", coordinates);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<FieldPostDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("coordinates");
        assertThat(violation.getMessage()).isEqualTo(
                "A valid field must have at least 4 point coordinates (including the closing point)."
        );
    }

    @Test
    @DisplayName("Try creating field when coordinate format not correct.")
    public void whenCoordinateIsNotPair_thenConstraintViolation() {

        List<List<Double>> polygon = new ArrayList<>();

        polygon.add(List.of(0.0, 0.0));
        polygon.add(List.of(0.0, 1.0));
        polygon.add(List.of(1.0)); // Invalid coordinate point, not a pair !!!
        polygon.add(List.of(0.0, 0.0));

        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(polygon);

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", coordinates);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<FieldPostDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("coordinates");
        assertThat(violation.getMessage()).isEqualTo(
                "Each coordinate must be a pair of doubles (longitude, latitude)."
        );
    }

    @Test
    @DisplayName("Try creating field when coordinate is invalid.")
    public void whenCoordinatesAreOutOfBound_thenConstraintViolation() {

        List<List<Double>> polygon = new ArrayList<>();

        polygon.add(List.of(-190.0, 0.0)); // Invalid longitude
        polygon.add(List.of(0.0, 1.0));
        polygon.add(List.of(180.0, 90.0));
        polygon.add(List.of(-190.0, 0.0)); // Closing point

        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(polygon);

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", coordinates);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<FieldPostDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("coordinates");
        assertThat(violation.getMessage()).isEqualTo("Invalid coordinates format.");
    }

    @Test
    @DisplayName("Try creating a field without the closing coordinates.")
    public void whenPolygonIsNotClosed_thenConstraintViolation() {

        List<List<Double>> polygon = new ArrayList<>();

        polygon.add(List.of(0.0, 0.0));
        polygon.add(List.of(0.0, 1.0));
        polygon.add(List.of(1.0, 1.0));
        polygon.add(List.of(1.0, 0.0)); // Not closing the polygon

        List<List<List<Double>>> coordinates = new ArrayList<>();
        coordinates.add(polygon);

        FieldPostDto fieldPostDto = new FieldPostDto("Field name", coordinates);

        Set<ConstraintViolation<FieldPostDto>> violations = validator.validate(fieldPostDto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<FieldPostDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("coordinates");
        assertThat(violation.getMessage()).isEqualTo(
                "The field needs to have the closing coordinates (same as the first coordinates)."
        );
    }
}
