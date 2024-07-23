package com.agrusi.backendapi.dto.response.field;

import java.util.List;

public record FieldResponseDto(
        String name,
        List<List<List<Double>>> coordinates,
        List<Double> center,
        SizeMap size
) { }
