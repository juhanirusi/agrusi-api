package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.model.Farm;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class FarmUnitTest {

    private Farm farm;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        farm = new Farm();
    }

    @AfterAll
    public static void tearDownValidator() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Create a public ID for the farm.")
    public void testCreatePublicId() {

        farm.createPublicId();
        assertNotNull(farm.getPublicId());
    }

    @Test
    @DisplayName("Test farm name length too short constraint.")
    public void testFarmNameTooShortViolation() {

        farm.setName("A");

        Set<ConstraintViolation<Farm>> violations = validator.validate(farm);

        assertEquals(1, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("Farm name is required, maximum 255 characters."));
    }

    @Test
    @DisplayName("Test farm name length too long constraint.")
    public void testFarmNameTooLongViolation() {

        String farmName = "A".repeat(256); // Because 255 is max !!!

        farm.setName(farmName);

        Set<ConstraintViolation<Farm>> violations = validator.validate(farm);

        assertEquals(1, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("Farm name is required, maximum 255 characters."));
    }

    @Nested
    class FarmCreationAndEqualsHashCodeTests {

        private Farm farm2;
        private ReflectionTestUtils reflectionTestUtils;

        @BeforeEach
        public void init() throws NoSuchFieldException, IllegalAccessException {

            farm = new Farm();
            farm2 = new Farm();
            reflectionTestUtils = new ReflectionTestUtils();

            // Using reflection to set private fields
            reflectionTestUtils.setField(farm, "id", 1L);
            reflectionTestUtils.setField(farm, "publicId", UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));

            reflectionTestUtils.setField(farm2, "id", 1L);
            reflectionTestUtils.setField(farm2, "publicId", UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));
        }

        @Test
        @DisplayName("Test creating a new farm.")
        public void testFarmCreation() {

            farm.setName("Jack's Farm");
            farm.setDateCreated(LocalDateTime.now());
            farm.setLastUpdated(LocalDateTime.now());

            assertAll(
                    "Grouped assertions of Farm",
                    () -> assertEquals(1L, farm.getId()),
                    () -> assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), farm.getPublicId()),
                    () -> assertEquals("Jack's Farm", farm.getName()),
                    () -> assertNotNull(farm.getDateCreated()),
                    () -> assertNotNull(farm.getLastUpdated())
            );
        }

        @Test
        @DisplayName("Test Farm equals() method.")
        public void testEquals() throws NoSuchFieldException, IllegalAccessException {

            // Test equals method
            assertEquals(farm, farm2);
            assertEquals(farm2, farm);

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(farm2, "publicId", UUID.fromString("a4ff504a-77b8-4983-9cc1-31de712b7eb8"));
            assertNotEquals(farm, farm2);
        }

        @Test
        @DisplayName("Test Farm hashCode() method.")
        public void testHashCode() throws NoSuchFieldException, IllegalAccessException {

            // Test hashCode method
            assertEquals(farm.hashCode(), farm2.hashCode());

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(farm2, "publicId", UUID.fromString("a4ff504a-77b8-4983-9cc1-31de712b7eb8"));
            assertNotEquals(farm.hashCode(), farm2.hashCode());
        }
    }
}
