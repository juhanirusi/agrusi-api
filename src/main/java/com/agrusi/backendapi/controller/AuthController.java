package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.auth.LoginDto;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.dto.response.auth.LoginResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Create (register) a new user REST API endpoint...

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {

        AccountRegistrationResponseDto account = authService.registerNewAccount(registerDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.CREATED,
                "Account created successfully.",
                account
        );
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {

        LoginResponseDto loggedInAccount = authService.login(loginDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Login successful.",
                loggedInAccount
        );
    }
}
