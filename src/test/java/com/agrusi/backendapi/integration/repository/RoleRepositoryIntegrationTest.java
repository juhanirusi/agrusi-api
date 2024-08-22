package com.agrusi.backendapi.integration.repository;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.enums.EAccountRole;
import com.agrusi.backendapi.model.Role;
import com.agrusi.backendapi.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class RoleRepositoryIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @Test
    @DisplayName("Find role by authority.")
    public void testFindAccountByEmail() {

        Optional<Role> userRole = roleRepository.findByAuthority(EAccountRole.USER);

        assertTrue(userRole.isPresent());
        assertEquals(userRole.get().getAuthority(), EAccountRole.USER.toString());

        // Let's double-check...

        Optional<Role> adminRole = roleRepository.findByAuthority(EAccountRole.ADMIN);

        assertTrue(adminRole.isPresent());
        assertEquals(adminRole.get().getAuthority(), EAccountRole.ADMIN.toString());
    }
}
