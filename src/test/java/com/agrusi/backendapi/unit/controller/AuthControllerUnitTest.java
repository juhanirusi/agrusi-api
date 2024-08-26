package com.agrusi.backendapi.unit.controller;

import com.agrusi.backendapi.UnitTest;
import com.agrusi.backendapi.controller.AuthController;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.service.AuthService;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private UUID publicId;

    private RegisterDto registerDto;
    private RegisterDto invalidRegisterDto;
    private AccountRegistrationResponseDto accountRegistrationResponseDto;

    @BeforeEach
    public void setUp() {

        publicId = UUID.randomUUID();

        registerDto = new RegisterDto(
                "Jack",
                "Farmer",
                "jack.farmer@example.com",
                "Password123"
        );

        invalidRegisterDto = new RegisterDto(
                "J",
                "F",
                "jack.farmerexample.com",
                "pass"
        );

        accountRegistrationResponseDto = new AccountRegistrationResponseDto(
                publicId,
                "jack.farmer@example.com",
                "Jack",
                "Farmer",
                false
        );
    }

    @Test
    @DisplayName("Register a new account.")
    public void testRegisterAccount() throws Exception {

        when(authService.registerNewAccount(any(RegisterDto.class))).thenReturn(
                accountRegistrationResponseDto
        );

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Account created successfully."))
                .andExpect(jsonPath("$.data.publicId").value(publicId.toString()))
                .andExpect(jsonPath("$.data.email").value("jack.farmer@example.com"))
                .andExpect(jsonPath("$.data.firstName").value("Jack"))
                .andExpect(jsonPath("$.data.lastName").value("Farmer"))
                .andExpect(jsonPath("$.data.accountVerified").value(false));
    }

    @Test
    @DisplayName("Try registering a new account with INVALID data.")
    public void testRegisterAccountValidationError() throws Exception {

        // No need to use "when", because this test is designed to check the
        // controller's behavior when invalid data is provided.

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray())

                // Should contain validation errors for all fields, because the names
                // are too short, email isn't valid and the password isn't valid...

                .andExpect(jsonPath("$.errors[0].field").exists())
                .andExpect(jsonPath("$.errors[1].field").exists())
                .andExpect(jsonPath("$.errors[2].field").exists())
                .andExpect(jsonPath("$.errors[3].field").exists());

        verify(authService, never()).registerNewAccount(any(RegisterDto.class));
    }

    // TODO --> ADD TEST FOR LOGGING IN
}
