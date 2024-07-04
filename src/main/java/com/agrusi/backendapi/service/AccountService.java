package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.account.AccountPutGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;

import java.util.UUID;

public interface AccountService {

    AccountFullResponseDto getAccountByPublicId(UUID publicId);

    AccountBasicResponseDto updateAccountBasicInfoByPublicIdPut(
            UUID publicId, AccountPutGeneralDto updateDto
    );

    void deleteAccountByPublicId(UUID publicId);
}
