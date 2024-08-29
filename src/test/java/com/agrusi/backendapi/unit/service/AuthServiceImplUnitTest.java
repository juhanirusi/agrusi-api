package com.agrusi.backendapi.unit.service;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.enums.EAccountRole;
import com.agrusi.backendapi.exception.auth.AccountWithEmailAlreadyExistException;
import com.agrusi.backendapi.mapper.AccountMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.Role;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.repository.RoleRepository;
import com.agrusi.backendapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private Account account;
    private Role userRole;

    private RegisterDto registerDto;
    private AccountRegistrationResponseDto registrationResponseDto;

    @BeforeEach
    public void setUp() {

        registerDto = new RegisterDto(
                "Jack",
                "Farmer",
                "jack.farmer@example.com",
                "password123"
        );

        registrationResponseDto = new AccountRegistrationResponseDto(
                UUID.randomUUID(),
                "jack.farmer@example.com",
                "Jack",
                "Farmer",
                false
        );

        userRole = new Role(EAccountRole.USER);
        account = new Account();
    }

    @Test
    @DisplayName("Register a new user account.")
    public void testRegisterNewAccount() {

        when(accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByAuthority(any(EAccountRole.class))).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(anyString())).thenReturn("password123");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toAccountRegistrationResponseDto(any(Account.class))).thenReturn(registrationResponseDto);

        AccountRegistrationResponseDto responseDto = authService.registerNewAccount(registerDto);

        verify(accountRepository).existsByEmail("jack.farmer@example.com");
        verify(roleRepository).findByAuthority(EAccountRole.USER);
        verify(accountRepository).save(any(Account.class));

        assertNotNull(responseDto);
        assertEquals(registrationResponseDto, responseDto);
    }

    @Test
    @DisplayName("Try registering an account (email) that already exists.")
    public void testRegisterNewAccountAccountAlreadyExists() {

        when(accountRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(
                AccountWithEmailAlreadyExistException.class,
                () -> authService.registerNewAccount(registerDto)
        );

        // Verify that save is never called since the exception
        // should short-circuit the process

        verify(accountRepository, never()).save(any(Account.class));
    }

    // TODO --> ADD TEST FOR LOGGING IN !!!
}
