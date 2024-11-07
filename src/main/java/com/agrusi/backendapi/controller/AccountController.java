package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
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

    @GetMapping(value = "/{accountPublicId}")
    public ResponseEntity<?> getAccount(@PathVariable UUID accountPublicId) {

        AccountFullResponseDto account = accountService.getAccountByPublicId(accountPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account details fetched successfully.",
                account
        );
    }

    @PatchMapping(value = "/{accountPublicId}")
    public ResponseEntity<?> updateAccountBasicInfoPatch(
            @PathVariable UUID accountPublicId,
            @Valid @RequestBody AccountPatchGeneralDto updateDto
    ) {
        AccountBasicResponseDto account = accountService.updateAccountBasicInfoByPublicIdPatch(
                accountPublicId, updateDto
        );

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account's general info updated successfully.",
                account
        );
    }

    @DeleteMapping(value = "/{accountPublicId}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID accountPublicId) {

        accountService.deleteAccountByPublicId(accountPublicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account deleted successfully.",
                Map.of("publicId", accountPublicId)
        );
    }
}
