package com.agrusi.backendapi.unit.service;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.exception.account.AccountWithPublicIdNotFoundException;
import com.agrusi.backendapi.mapper.AccountMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.AccountPreferences;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * @UnitTest --> Our custom annotation allowing us to run only
 * unit tests if we want to
*/

@UnitTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AccountServiceImpl accountService;

    private UUID publicId;
    private Account account;

    private AccountPatchGeneralDto accountPatchGeneralDto;

    private AccountFullResponseDto expectedAccountFullResponseDto;
    private AccountBasicResponseDto expectedAccountBasicResponseDto;

    @BeforeEach
    public void setUp() {

        publicId = UUID.randomUUID();

        account = new Account();

        account.setFirstName("Jack");
        account.setLastName("Farmer");
        account.setEmail("jack.farmer@example.com");

        AccountPreferences accountPreferences = new AccountPreferences();
        accountPreferences.setAccount(account);
        accountPreferences.setLanguage("fi");
        accountPreferences.setCurrency("EUR");
        accountPreferences.setTimeZone("Europe/Helsinki");
        accountPreferences.setFieldAreaUnit(EAreaUnit.HECTARE);

        account.setAccountPreferences(accountPreferences);

        accountPatchGeneralDto = new AccountPatchGeneralDto();
        accountPatchGeneralDto.setFirstName("Jane");
        accountPatchGeneralDto.setLastName("Rancher");
        accountPatchGeneralDto.setEmail(null);
        accountPatchGeneralDto.setPhoneNumber(null);

        expectedAccountFullResponseDto = new AccountFullResponseDto(
                publicId,
                "jack.farmer@example.com",
                "Jack",
                "Farmer",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        expectedAccountBasicResponseDto = new AccountBasicResponseDto(
                publicId,
                "jack.farmer@example.com",
                "+358123456789",
                "Jane",
                "Rancher",
                true
        );
    }

    @Test
    @DisplayName("Get an account with public ID.")
    public void testGetAccountByPublicIdSuccess() {

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountFullResponseDto(account)).thenReturn(expectedAccountFullResponseDto);

        AccountFullResponseDto responseDto = accountService.getAccountByPublicId(publicId);

        assertNotNull(responseDto);
        assertEquals(expectedAccountFullResponseDto, responseDto);
    }

    @Test
    @DisplayName("Get an (non-existent) account with public ID.")
    public void testGetAccountByPublicIdNotFound() {

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.empty());

        assertThrows(AccountWithPublicIdNotFoundException.class, () -> {
            accountService.getAccountByPublicId(publicId);
        });
    }

    @Test
    @DisplayName("Update an account's basic info.")
    public void testUpdateAccountBasicInfoByPublicId() {

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountBasicResponseDto(account)).thenReturn(expectedAccountBasicResponseDto);

        AccountBasicResponseDto responseDto = accountService.updateAccountBasicInfoByPublicIdPatch(
                publicId, accountPatchGeneralDto
        );

        // Check that the response contains correct data...
        assertNotNull(responseDto);
        assertEquals("Jane", responseDto.firstName());
        assertEquals("Rancher", responseDto.lastName());

        // Check that the new account has been updated...
        assertEquals("Jane", account.getFirstName());
        assertEquals("Rancher", account.getLastName());

        verify(accountRepository).findAccountByPublicId(publicId);
        verify(accountMapper).toAccountBasicResponseDto(account);
    }

    @Test
    @DisplayName("Delete an account by public ID.")
    public void testDeleteAccountByPublicId() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));
        when(authentication.getName()).thenReturn("jack.farmer@example.com");

        accountService.deleteAccountByPublicId(publicId);

        verify(accountRepository).delete(account);
    }

    @Test
    @DisplayName("Try deleting an account that doesn't exist.")
    public void testDeleteAccountByPublicIdNotFound() {

        UUID publicId = UUID.randomUUID();

        assertThrows(AccountWithPublicIdNotFoundException.class, () -> {
            accountService.deleteAccountByPublicId(publicId);
        });
    }
}
