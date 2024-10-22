package com.agrusi.backendapi.unit.service;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.dto.request.accountPreferences.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.accountPreferences.AccountPreferencesResponseDto;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.mapper.AccountPreferencesMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.AccountPreferences;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.impl.AccountPreferencesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
public class AccountPreferencesServiceImplUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountPreferencesMapper accountPreferencesMapper;

    @InjectMocks
    private AccountPreferencesServiceImpl accountPreferencesService;

    private UUID publicId;
    private Account account;

    private AccountPreferencesResponseDto expectedAccountPreferencesResponseDto;

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
    }

    @Test
    @DisplayName("Get account's account preferences.")
    public void testGetAccountPreferencesByAccountPublicId() {

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));

        when(accountPreferencesMapper.toAccountPreferencesResponseDto(account)).thenReturn(
                expectedAccountPreferencesResponseDto
        );

        AccountPreferencesResponseDto responseDto =
                accountPreferencesService.getAccountPreferencesByAccountPublicId(publicId);

        assertEquals(expectedAccountPreferencesResponseDto, responseDto);
    }

    @Test
    @DisplayName("Update account's account preferences.")
    public void testUpdateAccountPreferencesByPublicId() {

        String expectedLanguagePreference = "en-us";
        String expectedCurrencyPreference = "USD";
        String expectedTimeZonePreference = "America/New_York";
        String expectedFieldAreaUnitPreference = EAreaUnit.HECTARE.getUnitOfArea();

        AccountPreferencesPatchDto accountPreferencesPatchDto = new AccountPreferencesPatchDto();
        accountPreferencesPatchDto.setLanguage(expectedLanguagePreference);
        accountPreferencesPatchDto.setCurrency(expectedCurrencyPreference);
        accountPreferencesPatchDto.setTimeZone(expectedTimeZonePreference);
        accountPreferencesPatchDto.setFieldAreaUnit(expectedFieldAreaUnitPreference);

        expectedAccountPreferencesResponseDto = new AccountPreferencesResponseDto(
                publicId,
                expectedLanguagePreference,
                expectedCurrencyPreference,
                expectedTimeZonePreference,
                expectedFieldAreaUnitPreference
        );

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));

        when(accountPreferencesMapper.toAccountPreferencesResponseDto(account)).thenReturn(
                expectedAccountPreferencesResponseDto
        );

        AccountPreferencesResponseDto responseDto = accountPreferencesService.updateAccountPreferencesByAccountPublicIdPatch(
                publicId, accountPreferencesPatchDto
        );

        // Check that the response contains correct data...
        assertThat(responseDto).isEqualTo(expectedAccountPreferencesResponseDto);

        assertEquals(publicId, responseDto.publicId());
        assertEquals(expectedLanguagePreference, responseDto.language());
        assertEquals(expectedCurrencyPreference, responseDto.currency());
        assertEquals(expectedTimeZonePreference, responseDto.timeZone());
        assertEquals(expectedFieldAreaUnitPreference, responseDto.fieldAreaUnit());

        // Check that the new account preferences have been updated...
        assertEquals(expectedLanguagePreference, account.getAccountPreferences().getLanguage());
        assertEquals(expectedCurrencyPreference, account.getAccountPreferences().getCurrency());
        assertEquals(expectedTimeZonePreference, account.getAccountPreferences().getTimeZone());
        assertEquals(expectedFieldAreaUnitPreference, account.getAccountPreferences().getFieldAreaUnit().getUnitOfArea());

        verify(accountRepository).findAccountByPublicId(publicId);
        verify(accountPreferencesMapper).toAccountPreferencesResponseDto(account);
    }

    @Test
    @DisplayName("Fetch account's account preferences.")
    public void fetchAccountPreferencesByAccountPublicId() {

        when(accountRepository.findAccountByPublicId(publicId)).thenReturn(Optional.of(account));

        AccountPreferences fetchedAccountPreferences =
                accountPreferencesService.fetchAccountPreferencesByAccountPublicId(publicId);

        assertNotNull(fetchedAccountPreferences);
        assertEquals(account.getAccountPreferences(), fetchedAccountPreferences);
    }
}
