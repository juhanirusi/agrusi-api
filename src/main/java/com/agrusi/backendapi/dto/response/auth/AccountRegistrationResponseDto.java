package com.agrusi.backendapi.dto.response.auth;

import java.util.UUID;

public record AccountRegistrationResponseDto(
        UUID publicId,
        String email,
        String firstName,
        String lastName,
        boolean emailVerified
) { }
