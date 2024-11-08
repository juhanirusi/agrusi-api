package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.model.Field;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class FieldUnitTest {

    private Field field;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        field = new Field();
    }

    @AfterAll
    public static void tearDownValidator() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Test field name length too short constraint.")
    public void testFieldNameTooShortViolation() {

        field.setName("A");

        Set<ConstraintViolation<Field>> violations = validator.validate(field);

        assertEquals(1, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("Field name is required, maximum 255 characters."));
    }

    @Test
    @DisplayName("Test field name length too long constraint.")
    public void testFieldNameTooLongViolation() {

        String fieldName = "A".repeat(256); // Because 255 is max !!!

        field.setName(fieldName);

        Set<ConstraintViolation<Field>> violations = validator.validate(field);

        assertEquals(1, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("Field name is required, maximum 255 characters."));
    }

    @Nested
    class FieldCreationAndHashCodeEqualsTests {

        private Farm farm1;
        private Field field2;
        private ReflectionTestUtils reflectionTestUtils;

        @BeforeEach
        public void init() throws NoSuchFieldException, IllegalAccessException {

            farm1 = new Farm();
            Farm farm2 = new Farm();

            field2 = new Field();

            reflectionTestUtils = new ReflectionTestUtils();

            // Using reflection to set private fields
            reflectionTestUtils.setField(farm1, "id", 1L);
            reflectionTestUtils.setField(farm1, "publicId", UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));

            reflectionTestUtils.setField(farm2, "id", 1L);
            reflectionTestUtils.setField(farm2, "publicId", UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));

            reflectionTestUtils.setField(field, "id", 1L);
            field.setFarm(farm1);

            reflectionTestUtils.setField(field2, "id", 1L);
            field2.setFarm(farm2);
        }

        @Test
        @DisplayName("Test creating a new field.")
        public void testFieldCreation() {

            Polygon areaOfField = geometryFactory.createPolygon(new Coordinate[] {
                    new Coordinate(0, 0),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(0, 1),
                    new Coordinate(0, 0)
            });

            Point centerOfField = geometryFactory.createPoint(
                    new Coordinate(0,1)
            );

            field.setName("Farm field");
            field.setArea(areaOfField);
            field.setCenter(centerOfField);

            assertAll("Grouped assertions of Field",
                    () -> assertEquals(1L, field.getId()),
                    () -> assertEquals("Farm field", field.getName()),
                    () -> assertEquals(areaOfField, field.getArea()),
                    () -> assertEquals(centerOfField, field.getCenter()),
                    () -> assertEquals(farm1, field.getFarm())
            );
        }

        @Test
        @DisplayName("Test Field equals() method.")
        public void testEquals() throws NoSuchFieldException, IllegalAccessException {

            // Test equals method
            assertEquals(field, field2);
            assertEquals(field2, field);

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(field2, "id", 2L);

            assertNotEquals(field, field2);
        }

        @Test
        @DisplayName("Test Field hashCode() method.")
        public void testHashCode() throws NoSuchFieldException, IllegalAccessException {

            assertEquals(field.hashCode(), field2.hashCode());
            assertEquals(field2.hashCode(), field.hashCode());

            // Modify one field to ensure objects are not equal...
            reflectionTestUtils.setField(field, "id", 2L);
            assertNotEquals(field.hashCode(), field2.hashCode());

            // Modify "field2" to make the objects equal again...
            reflectionTestUtils.setField(field2, "id", 2L);
            assertEquals(field2.hashCode(), field.hashCode());

            // Modify "farm1" to ensure objects are not equal...
            reflectionTestUtils.setField(farm1, "publicId", UUID.fromString("a4ff504a-77b8-4983-9cc1-31de712b7eb8"));
            assertNotEquals(field.hashCode(), field2.hashCode());
        }
    }
}
