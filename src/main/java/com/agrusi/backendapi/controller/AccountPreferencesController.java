package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.accountPreferences.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.accountPreferences.AccountPreferencesResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.AccountPreferencesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/accounts/{accountPublicId}/preferences")
public class AccountPreferencesController {

    private final AccountPreferencesService accountPreferencesService;

    public AccountPreferencesController(
            AccountPreferencesService accountPreferencesService
    ) {
        this.accountPreferencesService = accountPreferencesService;
    }

    // Get (GET) Account Preferences

    @GetMapping
    public ResponseEntity<?> getAccountPreferences(@PathVariable UUID accountPublicId) {

        AccountPreferencesResponseDto account =
                accountPreferencesService.getAccountPreferencesByAccountPublicId(accountPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account preferences fetched successfully.",
                account
        );
    }

    // Update (PATCH) Account Preferences

    @PatchMapping
    public ResponseEntity<?> updateAccountPreferencesPatch(
            @PathVariable UUID accountPublicId,
            @Valid @RequestBody AccountPreferencesPatchDto updateDto
    ) {
        AccountPreferencesResponseDto accountPreferences =
                accountPreferencesService.updateAccountPreferencesByAccountPublicIdPatch(accountPublicId, updateDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account preferences updated successfully.",
                accountPreferences
        );
    }
}
