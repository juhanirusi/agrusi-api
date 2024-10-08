package com.agrusi.backendapi.dto.response.account;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountFullResponseDto(
        UUID publicId,
        String email,
        String firstName,
        String lastName,
        boolean emailVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
