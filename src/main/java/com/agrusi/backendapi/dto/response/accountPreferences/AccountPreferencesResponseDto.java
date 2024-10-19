package com.agrusi.backendapi.dto.response.accountPreferences;

import java.util.UUID;

public record AccountPreferencesResponseDto(
        UUID publicId,
        String language,
        String currency,
        String timeZone,
        String fieldAreaUnit
) { }
