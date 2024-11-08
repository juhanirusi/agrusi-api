package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;

import java.util.UUID;

public interface AccountService {

    AccountFullResponseDto getAccountByPublicId(UUID accountPublicId);

    AccountBasicResponseDto updateAccountBasicInfoByPublicIdPatch(
            UUID accountPublicId, AccountPatchGeneralDto updateDto
    );

    void deleteAccountByPublicId(UUID accountPublicId);
}
