package com.agrusi.backendapi.dto.response.account;

import java.util.UUID;

public record AccountBasicResponseDto(
        UUID publicId,
        String email,
        String firstName,
        String lastName,
        boolean accountVerified
) { }
