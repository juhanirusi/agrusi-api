package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.validator.annotation.ValidCoordinates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CoordinatesValidator implements
        ConstraintValidator<ValidCoordinates, List<List<List<Double>>>> {

    @Override
    public boolean isValid(List<List<List<Double>>> value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {

            context.buildConstraintViolationWithTemplate(
                    "Coordinates can't be empty."
            ).addConstraintViolation();

            return false; // When the coordinates are empty or null
        }

        for (List<List<Double>> polygon : value) {
            if (!isValidPolygon(polygon, context)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidPolygon(List<List<Double>> polygon, ConstraintValidatorContext context) {

        if (polygon.size() < 4) {
            context.buildConstraintViolationWithTemplate(
                    "A valid field must have at least 4 point coordinates (including the closing point)."
            ).addConstraintViolation();

            return false; // A valid polygon must have at least 4 points (including the closing point)
        }

        for (List<Double> point : polygon) {

            if (point.size() != 2) {
                context.buildConstraintViolationWithTemplate(
                        "Each coordinate must be a pair of doubles (longitude, latitude)."
                ).addConstraintViolation();

                return false; // Each coordinate must be a pair of doubles (longitude, latitude)
            }

            Double longitude = point.get(0);
            Double latitude = point.get(1);

            if (longitude == null || latitude == null || longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90) {
                return false; // Longitude must be between -180 and 180, and latitude between -90 and 90
            }
        }

        // Check if the first and last point are the same for a closed polygon
        List<Double> firstPoint = polygon.getFirst();
        List<Double> lastPoint = polygon.getLast();

        if (!firstPoint.equals(lastPoint)) {
            context.buildConstraintViolationWithTemplate(
                    "The field needs to have the closing coordinates (same as the first coordinates)."
            ).addConstraintViolation();

            return false;
        }

        return true;
    }
}
