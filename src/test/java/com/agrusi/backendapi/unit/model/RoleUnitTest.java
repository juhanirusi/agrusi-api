package com.agrusi.backendapi.unit.model;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.enums.EAccountRole;
import com.agrusi.backendapi.model.Role;
import com.agrusi.backendapi.unit.util.ReflectionTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
public class RoleUnitTest {

    @Test
    @DisplayName("Test authority with USER role.")
    public void testGetAuthorityUserRole() {

        Role role = new Role(1L, EAccountRole.USER);

        assertEquals(1L, role.getId());
        assertEquals("USER", role.getAuthority());
    }

    @Test
    @DisplayName("Test authority with ADMIN role.")
    public void testAuthorityAdminRole() {

        Role role = new Role(1L, EAccountRole.ADMIN);

        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getAuthority());
    }

    @Test
    @DisplayName("Test Role equals() method.")
    public void testEquals() throws NoSuchFieldException, IllegalAccessException {

        ReflectionTestUtils reflectionTestUtils = new ReflectionTestUtils();

        Role role1 = new Role(1L, EAccountRole.USER);
        Role role2 = new Role(1L, EAccountRole.ADMIN);

        // Test equals method...
        assertEquals(role1, role2);

        // Modify one field to ensure objects are not equal...
        reflectionTestUtils.setField(role2, "id", 2L);

        assertNotEquals(role1, role2);
    }

    @Test
    @DisplayName("Test Role hashCode() method.")
    public void testHashCode() throws NoSuchFieldException, IllegalAccessException {

        ReflectionTestUtils reflectionTestUtils = new ReflectionTestUtils();

        Role role1 = new Role(1L, EAccountRole.USER);
        Role role2 = new Role(1L, EAccountRole.ADMIN);

        // Test hashCode method...
        assertEquals(role1.hashCode(), role2.hashCode());

        // Modify one field to ensure objects are not equal...
        reflectionTestUtils.setField(role2, "id", 2L);

        assertNotEquals(role1.hashCode(), role2.hashCode());
    }
}
