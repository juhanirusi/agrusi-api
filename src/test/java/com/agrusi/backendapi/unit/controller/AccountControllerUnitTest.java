package com.agrusi.backendapi.unit.controller;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.controller.AccountController;
import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.request.account.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountPreferencesResponseDto;
import com.agrusi.backendapi.exception.account.AccountWithPublicIdNotFoundException;
import com.agrusi.backendapi.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* @UnitTest --> Our custom annotation allowing us to run only
* unit tests if we want to.
*
* @AutoConfigureMockMvc(addFilters = false) --> Disable security
* filters for these tests, and test Spring Security in separate
* tests, this class is for testing controller logic only.
*/

@UnitTest
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    private UUID publicId;
    private UUID nonExistentPublicId;

    @BeforeEach
    public void setUp() {

        publicId = UUID.randomUUID();
        nonExistentPublicId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Get an account.")
    public void testGetAccount() throws Exception {

        String email = "jack.farmer@example.com";
        String firstName = "Jack";
        String lastName = "Farmer";
        boolean emailVerified = true;

        AccountFullResponseDto responseDto = new AccountFullResponseDto(
                publicId,
                email,
                firstName,
                lastName,
                emailVerified,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(accountService.getAccountByPublicId(publicId)).thenReturn(
                responseDto
        );

        mockMvc.perform(get("/api/v1/accounts/{publicId}", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account details fetched successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.firstName").value(firstName))
                .andExpect(jsonPath("$.data.lastName").value(lastName))
                .andExpect(jsonPath("$.data.emailVerified").value(emailVerified));
    }

    @Test
    @DisplayName("Get an account that doesn't exist.")
    public void testGetAccount_NotFound() throws Exception {

        when(accountService.getAccountByPublicId(nonExistentPublicId)).thenThrow(
                new AccountWithPublicIdNotFoundException(nonExistentPublicId)
        );

        mockMvc.perform(get("/api/v1/accounts/{publicId}", nonExistentPublicId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Account not found."))
                .andExpect(jsonPath("$.errors[0].message").value(
                        "Account with public ID '" + nonExistentPublicId + "' wasn't found."
                ));
    }

    @Test
    @DisplayName("Update (PATCH) an account's general info.")
    public void testUpdateAccountBasicInfoPatch() throws Exception {

        String updateFirstNameTo = "Jane";
        String updateLastNameTo = "Rancher";
        String updateEmailTo = "jack.farmer@example.com";
        String updatePhoneNumberTo = "+358501234567";

        String email = "jack.farmer@example.com";
        String phoneNumber = "+358501234567";
        String firstName = "Jane";
        String lastName = "Rancher";
        boolean emailVerified = true;

        AccountPatchGeneralDto updateDto = new AccountPatchGeneralDto(
                updateFirstNameTo,
                updateLastNameTo,
                updateEmailTo,
                updatePhoneNumberTo
        );

        AccountBasicResponseDto responseDto = new AccountBasicResponseDto(
                publicId,
                email,
                phoneNumber,
                firstName,
                lastName,
                emailVerified
        );

        when(accountService.updateAccountBasicInfoByPublicIdPatch(
                any(UUID.class), any(AccountPatchGeneralDto.class)
        )).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/accounts/{publicId}", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account's general info updated successfully."))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.phoneNumber").value(phoneNumber))
                .andExpect(jsonPath("$.data.firstName").value(firstName))
                .andExpect(jsonPath("$.data.lastName").value(lastName))
                .andExpect(jsonPath("$.data.emailVerified").value(emailVerified));
    }

    @Test
    @DisplayName("Try updating (PATCH) account basic info with INVALID data.")
    public void testUpdateAccountBasicInfoPatchValidationError() throws Exception {

        AccountPatchGeneralDto invalidUpdateDto = new AccountPatchGeneralDto(
                "J",
                "R",
                null,
                "+358501234567"
        );

        // No need to use "when", because this test is designed to check the
        // controller's behavior when invalid data is provided.

        mockMvc.perform(patch("/api/v1/accounts/{publicId}", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray())

                // Should contain validation errors for both fields,
                // because the names are too short...

                .andExpect(jsonPath("$.errors[0].field").exists())
                .andExpect(jsonPath("$.errors[1].field").exists());

        verify(accountService, never()).updateAccountBasicInfoByPublicIdPatch(any(UUID.class), any(AccountPatchGeneralDto.class));
    }

    @Test
    @DisplayName("Delete an account.")
    public void testDeleteAccount() throws Exception {

        doNothing().when(accountService).deleteAccountByPublicId(publicId);

        mockMvc.perform(delete("/api/v1/accounts/{publicId}", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account deleted successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()));
    }

    @Test
    @DisplayName("Get account preferences.")
    public void testGetAccountPreferences() throws Exception {

        String languageCode = "fi";
        String currencyCode = "EUR";
        String timeZone = "Europe/Helsinki";

        AccountPreferencesResponseDto responseDto = new AccountPreferencesResponseDto(
                publicId,
                languageCode,
                currencyCode,
                timeZone
        );

        when(accountService.getAccountPreferencesByPublicId(publicId)).thenReturn(
                responseDto
        );

        mockMvc.perform(get("/api/v1/accounts/{publicId}/preferences", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account preferences fetched successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.language").value(languageCode))
                .andExpect(jsonPath("$.data.currency").value(currencyCode))
                .andExpect(jsonPath("$.data.timeZone").value(timeZone));
    }

    @Test
    @DisplayName("Update (PATCH) account preferences.")
    public void testUpdateAccountPreferencesPatch() throws Exception {

        String languageCode = "en-us";
        String currencyCode = "USD";
        String timeZone = "America/New_York";

        AccountPreferencesResponseDto responseDto = new AccountPreferencesResponseDto(
                publicId,
                languageCode,
                currencyCode,
                timeZone
        );

        AccountPreferencesPatchDto patchDto = new AccountPreferencesPatchDto(
                languageCode,
                currencyCode,
                timeZone
        );

        when(accountService.updateAccountPreferencesByPublicIdPatch(
                any(UUID.class), any(AccountPreferencesPatchDto.class)
        )).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/accounts/{publicId}/preferences", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account preferences updated successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.language").value(languageCode))
                .andExpect(jsonPath("$.data.currency").value(currencyCode))
                .andExpect(jsonPath("$.data.timeZone").value(timeZone));
    }
}
