package com.agrusi.backendapi.service;

import com.agrusi.backendapi.dto.request.accountPreferences.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.accountPreferences.AccountPreferencesResponseDto;
import com.agrusi.backendapi.model.AccountPreferences;

import java.util.UUID;

public interface AccountPreferencesService {

    AccountPreferences fetchAccountPreferencesByAccountPublicId(UUID accountPublicId);

    AccountPreferencesResponseDto getAccountPreferencesByAccountPublicId(UUID accountPublicId);

    AccountPreferencesResponseDto updateAccountPreferencesByAccountPublicIdPatch(
            UUID accountPublicId, AccountPreferencesPatchDto accountPreferencesPatchDto
    );

    AccountPreferences getCurrentAuthenticatedUserPreferences();
}
