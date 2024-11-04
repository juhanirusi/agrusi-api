package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.enums.EAddressType;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.model.*;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class AccountUnitTest {

    private Account account;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        account = new Account();
    }

    @AfterAll
    public static void tearDownValidator() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Create a public ID for the account.")
    public void testCreatePublicId() {

        account.createPublicId();
        assertNotNull(account.getPublicId());
    }

    @Test
    @DisplayName("Get account's full name.")
    public void testGetFullName() {

        account.setFirstName("Jack");
        account.setLastName("Farmer");

        assertEquals("Jack Farmer", account.getFullName());
    }

    @Test
    @DisplayName("Test that email is correct form.")
    public void testEmailPattern() {

        account.setEmail("jack.farmerexample.com"); // Email needs to contain @ at the right spot !!!

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(1, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertEquals("Please provide a valid email address.", messages.iterator().next());
    }

    @Test
    @DisplayName("Test first and last name length too short constraint.")
    public void testAccountFirstAndLastNameTooShortViolation() {

        account.setFirstName("A");
        account.setLastName("A");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(2, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("First name is required, maximum 255 characters."));
        assertTrue(messages.contains("Last name is required, maximum 255 characters."));
    }

    @Test
    @DisplayName("Test first and last name length too long constraint.")
    public void testAccountFirstAndLastNameTooLongViolation() {

        String name = "A".repeat(256); // Because 255 is max !!!

        account.setFirstName(name);
        account.setLastName(name);

        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(2, violations.size());

        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        assertTrue(messages.contains("First name is required, maximum 255 characters."));
        assertTrue(messages.contains("Last name is required, maximum 255 characters."));
    }

    @Nested
    class AccountCreationAndEqualsHashCodeTests {

        private Account account2;
        private ReflectionTestUtils reflectionTestUtils;

        @BeforeEach
        public void init() throws NoSuchFieldException, IllegalAccessException {

            account = new Account();
            account2 = new Account();
            reflectionTestUtils = new ReflectionTestUtils();

            // Using reflection to set private fields
            reflectionTestUtils.setField(account, "id", 1L);
            reflectionTestUtils.setField(
                    account,
                    "publicId",
                    UUID.fromString("123e4567-e89b-12d3-a456-556642440000")
            );

            reflectionTestUtils.setField(account2, "id", 1L);
            reflectionTestUtils.setField(account2,
                    "publicId",
                    UUID.fromString("123e4567-e89b-12d3-a456-556642440000")
            );
        }

        @Test
        @DisplayName("Test creating a new user account.")
        public void testAccountCreation() {

            // Arrange: Set up the account entity with expected values

            String firstName = "Jack";
            String lastName = "Farmer";
            String email = "jack.farmer@example.com";
            String password = "password123";

            String languagePreference = "fi";
            String currencyPreference = "EUR";
            String timeZonePreference = "Europe/Helsinki";
            EAreaUnit fieldAreaUnit = EAreaUnit.HECTARE;

            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setEmail(email);
            account.setPassword(password);

            UserProfile userProfile = new UserProfile(
                    1L,
                    account
            );

            AccountPreferences accountPreferences = new AccountPreferences();
            accountPreferences.setAccount(account);
            accountPreferences.setLanguage(languagePreference);
            accountPreferences.setCurrency(currencyPreference);
            accountPreferences.setTimeZone(timeZonePreference);
            accountPreferences.setFieldAreaUnit(fieldAreaUnit);

            Set<EAddressType> addressTypes = Set.of(EAddressType.HOME);

            Address exampleAddress = new Address(
                    1L,
                    "My Street 123",
                    "Kingston",
                    "New York",
                    "12401",
                    "United States",
                    account,
                    true,
                    addressTypes
            );

            Set<Address> addresses = Set.of(exampleAddress);

            account.setUserProfile(userProfile);
            account.setAccountPreferences(accountPreferences);
            account.setAddresses(addresses);
            account.setAuthorities(new HashSet<>());

            assertAll(
                    "Grouped assertions of Account",
                    () -> assertEquals(1L, account.getId()),
                    () -> assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), account.getPublicId()),
                    () -> assertEquals(firstName, account.getFirstName()),
                    () -> assertEquals(lastName, account.getLastName()),
                    () -> assertEquals(email, account.getEmail()),
                    () -> assertEquals(password, account.getPassword()),

                    () -> assertEquals(account, account.getUserProfile().getAccount()),

                    () -> assertEquals(account, account.getAccountPreferences().getAccount()),
                    () -> assertEquals(languagePreference, account.getAccountPreferences().getLanguage()),
                    () -> assertEquals(currencyPreference, account.getAccountPreferences().getCurrency()),
                    () -> assertEquals(timeZonePreference, account.getAccountPreferences().getTimeZone()),
                    () -> assertEquals(fieldAreaUnit.getUnitOfArea(), account.getAccountPreferences().getFieldAreaUnit().getUnitOfArea()),

                    () -> assertTrue(account.getAddresses().contains(exampleAddress)),

                    () -> assertTrue(account.getAuthorities().isEmpty()),
                    () -> assertFalse(account.isEmailVerified())
            );
        }

        @Test
        @DisplayName("Test Account equals() method.")
        public void testEquals() throws NoSuchFieldException, IllegalAccessException {

            // Test equals method
            assertEquals(account, account2);
            assertEquals(account2, account);

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(
                    account2,
                    "publicId",
                    UUID.fromString("a4ff504a-77b8-4983-9cc1-31de712b7eb8")
            );

            assertNotEquals(account, account2);
        }

        @Test
        @DisplayName("Test Account hashCode() method.")
        public void testHashCode() throws NoSuchFieldException, IllegalAccessException {

            // Test hashCode method
            assertEquals(account.hashCode(), account2.hashCode());

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(
                    account2,
                    "publicId",
                    UUID.fromString("a4ff504a-77b8-4983-9cc1-31de712b7eb8")
            );

            assertNotEquals(account.hashCode(), account2.hashCode());
        }
    }
}
