package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.account.AccountPatchGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;

import java.util.UUID;

public interface AccountService {

    AccountFullResponseDto getAccountByPublicId(UUID publicId);

    AccountBasicResponseDto updateAccountBasicInfoByPublicIdPatch(
            UUID publicId, AccountPatchGeneralDto updateDto
    );

    void deleteAccountByPublicId(UUID publicId);

    // Account Preferences

//    AccountPreferencesResponseDto getAccountPreferencesByAccountPublicId(UUID publicId);
//
//    AccountPreferencesResponseDto updateAccountPreferencesByAccountPublicIdPatch(
//            UUID publicId, AccountPreferencesPatchDto accountPreferencesPatchDto
//    );
}
