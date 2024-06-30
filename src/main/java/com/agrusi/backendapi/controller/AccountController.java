package com.agrusi.backendapi.controller;

import com.agrusi.backendapi.dto.request.account.AccountPutGeneralDto;
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

    @GetMapping(value = "/{publicId}")
    public ResponseEntity<?> getAccount(@PathVariable UUID publicId) {

        AccountFullResponseDto account = accountService.getAccountByPublicId(publicId);

        return ResponseHandler.generateSuccessResponse(
                HttpStatus.OK,
                "Account details fetched successfully.",
                account
        );
    }

    @PutMapping(value = "/{publicId}")
    public ResponseEntity<?> updateAccountBasicInfoPut(
            @PathVariable UUID publicId,
            @Valid @RequestBody AccountPutGeneralDto updateDto
    ) {
        AccountBasicResponseDto account = accountService.updateAccountBasicInfoByPublicIdPut(
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
}
