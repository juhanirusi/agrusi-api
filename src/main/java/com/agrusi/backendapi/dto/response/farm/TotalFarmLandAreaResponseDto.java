package com.agrusi.backendapi.dto.response.farm;

import com.agrusi.backendapi.dto.response.SizeMap;

import java.util.UUID;

public record TotalFarmLandAreaResponseDto(
        UUID publicId,
        String name,
        SizeMap size
) { }
