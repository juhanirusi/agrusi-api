package com.agrusi.backendapi.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterAccountResponseDto (
        UUID id,
        String email,
        String firstName,
        String lastName,
        boolean accountVerified,
        LocalDateTime dateCreated
) { }
