package com.agrusi.backendapi.dto.response.farm;

import java.util.UUID;

public record FarmResponseDto(
        UUID publicId,
        String name
) { }
