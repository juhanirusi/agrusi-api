package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.auth.LoginDto;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.dto.response.auth.LoginResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.security.service.AuthUserDetails;
import com.agrusi.backendapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    // Log in an existing user REST API endpoint...
    // TODO --> COMPLETE THIS BY ADDING ERROR HANDLING AS WELL AS REMOVING
    //  THE TESTING METHODS AT THE BOTTOM

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {

        LoginResponseDto loggedInAccount = authService.login(loginDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Login successful.",
                loggedInAccount
//                new LinkedHashMap<>() {{
//                    put("email", loggedInAccount.email());
//                    put("accessToken", loggedInAccount.token()); // TODO --> CHANGE NAME OF FIELD !!!
//                }}
        );
    }

    @GetMapping(value = "/secured")
    public String secured() {
        return "YOU GET TO THE PAGE";
    }

    @GetMapping(value = "/user")
    public Principal user(Principal user) {

        return user;
    }

    @GetMapping(value = "/test_fetching_user_details")
    private String fetchUserDetails() {

        // Get the currently authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String toReturn = "";

        // Make sure the principal is an instance of your custom AuthUserDetails
        if (authentication != null && authentication.getPrincipal() instanceof AuthUserDetails userDetails) {

            // Access the custom method
            boolean accountEnabled = userDetails.isEnabled();
            toReturn = "The user account is enabled: " + accountEnabled;
        }
        else {
            toReturn = "ERROR";
        }

        return toReturn;
    }
}
