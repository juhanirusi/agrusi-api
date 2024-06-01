package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.LoginDto;
import com.agrusi.backendapi.dto.request.RegisterDto;
import com.agrusi.backendapi.dto.response.LoginResponseDto;
import com.agrusi.backendapi.dto.response.RegisterAccountResponseDto;
import com.agrusi.backendapi.exception.auth.AccountWithEmailAlreadyExistException;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Create (register) a new user REST API endpoint...

    @PostMapping(value = "/register") // PERHAPS REPLACE <Object> WITH <?>
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {

        try {
            RegisterAccountResponseDto createdAccount = authService.registerNewAccount(registerDto);

            return ResponseHandler.generateResponse(
                    HttpStatus.CREATED,
                    "success",
                    "Account created successfully.",
                    new LinkedHashMap<>() {{
                        put("id", createdAccount.publicId());
                        put("email", createdAccount.email());
                        put("firstName", createdAccount.firstName());
                        put("lastName", createdAccount.lastName());
                        put("accountVerified", createdAccount.accountVerified());
                        put("dateCreated", createdAccount.dateCreated());
                    }}
            );
        } catch (AccountWithEmailAlreadyExistException exception) {

            return ResponseHandler.generateResponse(
                    HttpStatus.CONFLICT,
                    "error",
                    "Account creation failed",
                    Map.of("message", exception.getMessage())
            );
        }
    }

    // Log in an existing user REST API endpoint...

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {

        LoginResponseDto loggedInAccount = authService.login(loginDto);

        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                "success",
                "Login successful.",
                new LinkedHashMap<>() {{
                    put("email", loggedInAccount.email());
                    put("accessToken", loggedInAccount.token());
                }}
        );
    }

    @GetMapping(value = "/secured")
    public String secured() {
        return "YOU GET TO THE PAGE";
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
