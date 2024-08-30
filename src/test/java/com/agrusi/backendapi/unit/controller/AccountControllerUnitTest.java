package com.agrusi.backendapi.unit.controller;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.controller.AccountController;
import com.agrusi.backendapi.dto.request.account.AccountPutGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
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

    private AccountFullResponseDto expectedAccountFullResponseDto;
    private AccountPutGeneralDto updateAccountBasicInfoPutDto;
    private AccountPutGeneralDto invalidUpdateDto;
    private AccountBasicResponseDto accountBasicResponseDto;

    @BeforeEach
    public void setUp() {

        publicId = UUID.randomUUID();
        nonExistentPublicId = UUID.randomUUID();

        expectedAccountFullResponseDto = new AccountFullResponseDto(
                publicId,
                "jack.farmer@example.com",
                "Jack",
                "Farmer",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        updateAccountBasicInfoPutDto = new AccountPutGeneralDto("Jane", "Rancher");

        invalidUpdateDto = new AccountPutGeneralDto(
                "J",
                "R"
        );

        accountBasicResponseDto = new AccountBasicResponseDto(
                publicId,
                "jack.farmer@example.com",
                "Jane",
                "Rancher",
                true
        );
    }

    @Test
    @DisplayName("Get an account.")
    public void testGetAccount() throws Exception {

        when(accountService.getAccountByPublicId(publicId)).thenReturn(
                expectedAccountFullResponseDto
        );

        mockMvc.perform(get("/api/v1/accounts/{publicId}", publicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account details fetched successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.firstName").value("Jack"))
                .andExpect(jsonPath("$.data.lastName").value("Farmer"))
                .andExpect(jsonPath("$.data.email").value("jack.farmer@example.com"))
                .andExpect(jsonPath("$.data.accountVerified").value(true));
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
    @DisplayName("Update (PUT) an account's general info.")
    public void testUpdateAccountBasicInfoPut() throws Exception {

        when(accountService.updateAccountBasicInfoByPublicIdPut(
                any(UUID.class), any(AccountPutGeneralDto.class)
        )).thenReturn(accountBasicResponseDto);

        mockMvc.perform(put("/api/v1/accounts/{publicId}", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAccountBasicInfoPutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account's general info updated successfully."))
                .andExpect(jsonPath("$.data.firstName").value("Jane"))
                .andExpect(jsonPath("$.data.lastName").value("Rancher"));
    }

    @Test
    @DisplayName("Try updating (PUT) account basic info with INVALID data.")
    public void testUpdateAccountBasicInfoPutValidationError() throws Exception {

        // No need to use "when", because this test is designed to check the
        // controller's behavior when invalid data is provided.

        mockMvc.perform(put("/api/v1/accounts/{publicId}", publicId)
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

        verify(accountService, never()).updateAccountBasicInfoByPublicIdPut(any(UUID.class), any(AccountPutGeneralDto.class));
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
}