package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.request.account.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountPreferencesResponseDto;
import com.agrusi.backendapi.handler.ResponseHandler;
import com.agrusi.backendapi.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{publicId}")
    public ResponseEntity<?> getAccount(@PathVariable UUID publicId) {

        AccountFullResponseDto account = accountService.getAccountByPublicId(publicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account details fetched successfully.",
                account
        );
    }

    @PatchMapping(value = "/{publicId}")
    public ResponseEntity<?> updateAccountBasicInfoPatch(
            @PathVariable UUID publicId,
            @Valid @RequestBody AccountPatchGeneralDto updateDto
    ) {
        AccountBasicResponseDto account = accountService.updateAccountBasicInfoByPublicIdPatch(
                publicId, updateDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account's general info updated successfully.",
                account
        );
    }

    @DeleteMapping(value = "/{publicId}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID publicId) {

        accountService.deleteAccountByPublicId(publicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account deleted successfully.",
                Map.of("publicId", publicId)
        );
    }

    // Get (GET) Account Preferences

    @GetMapping(value = "/{publicId}/preferences")
    public ResponseEntity<?> getAccountPreferences(@PathVariable UUID publicId) {

        AccountPreferencesResponseDto account =
                accountService.getAccountPreferencesByPublicId(publicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account preferences fetched successfully.",
                account
        );
    }

    // Update (PATCH) Account Preferences

    @PatchMapping(value = "/{publicId}/preferences")
    public ResponseEntity<?> updateAccountPreferencesPatch(
            @PathVariable UUID publicId,
            @Valid @RequestBody AccountPreferencesPatchDto updateDto
    ) {
        AccountPreferencesResponseDto accountPreferences =
                accountService.updateAccountPreferencesByPublicIdPatch(publicId, updateDto);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account preferences updated successfully.",
                accountPreferences
        );
    }
}
