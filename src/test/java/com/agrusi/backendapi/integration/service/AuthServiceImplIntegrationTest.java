package com.agrusi.backendapi.integration.service;

import com.agrusi.backendapi.IntegrationTest;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @IntegrationTest --> Our custom annotation allowing us to run only
 * integration tests if we want to
*/

@IntegrationTest
@SpringBootTest
@Transactional
public class AuthServiceImplIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Test creating an account.")
    public void testRegisterNewAccount() {

        RegisterDto registerDto = new RegisterDto(
                "Jack",
                "Farmer",
                "jack.farmer@example.com",
                "password123"
        );

        AccountRegistrationResponseDto response = authService.registerNewAccount(registerDto);

        assertNotNull(response);
        assertTrue(accountRepository.existsByEmail("jack.farmer@example.com"));
    }

    // TODO --> ADD TEST FOR LOGGING IN !!!
}
