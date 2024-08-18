package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.UserAddress;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class UserAddressUnitTest {

    private UserAddress userAddress;

    private Account account;

    @BeforeEach
    public void setUp() {

        account = new Account();

        userAddress = new UserAddress(
                1L,
                "placeholder",
                "placeholder",
                "placeholder",
                "placeholder",
                "placeholder",
                false,
                account
        );
    }

    @Test
    @DisplayName("Test creation of UserAddress.")
    public void testUserAddressCreation() {

        // Set public fields with setters...
        userAddress.setStreetAddress("My Street 123");
        userAddress.setCity("Kingston");
        userAddress.setProvince("New York");
        userAddress.setPostalCode("12401");
        userAddress.setCountry("United States");
        userAddress.setDefault(true);

        assertAll(
                "Grouped assertions of UserAddress",
                () -> assertEquals("My Street 123", userAddress.getStreetAddress()),
                () -> assertEquals("Kingston", userAddress.getCity()),
                () -> assertEquals("New York", userAddress.getProvince()),
                () -> assertEquals("12401", userAddress.getPostalCode()),
                () -> assertEquals("United States", userAddress.getCountry()),
                () -> assertTrue(userAddress.isDefault()),
                () -> assertEquals(account, userAddress.getAccount())
        );
    }

    @Nested
    class UserAddressEqualsHashCodeTests {

        private Account account2;
        private UserAddress userAddress2;
        private ReflectionTestUtils reflectionTestUtils;

        @BeforeEach
        public void init() {

            account2 = new Account();
            reflectionTestUtils = new ReflectionTestUtils();

            userAddress2 = new UserAddress(
                    1L,
                    "placeholder",
                    "placeholder",
                    "placeholder",
                    "placeholder",
                    "placeholder",
                    false,
                    account2
            );
        }

        @Test
        @DisplayName("Test UserAddress equals() method.")
        public void testEquals() throws NoSuchFieldException, IllegalAccessException {

            assertEquals(userAddress, userAddress2);
            assertEquals(userAddress2, userAddress);

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(account2, "id", 2L);

            assertNotEquals(userAddress, userAddress2);
        }

        @Test
        @DisplayName("Test UserAddress hashCode() method.")
        public void testHashCode() throws NoSuchFieldException, IllegalAccessException {

            assertEquals(userAddress.hashCode(), userAddress2.hashCode());
            assertEquals(userAddress2.hashCode(), userAddress.hashCode());

            // Modify one field to ensure objects are not equal
            reflectionTestUtils.setField(account2, "id", 2L);

            assertNotEquals(userAddress.hashCode(), userAddress2.hashCode());
        }
    }
}
