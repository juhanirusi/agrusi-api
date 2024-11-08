package com.agrusi.backendapi.dto.response.field;

import com.agrusi.backendapi.dto.response.SizeMap;

import java.util.List;

public record FieldResponseDto(
        Long id,
        String name,
        List<List<List<Double>>> coordinates,
        List<Double> center,
        SizeMap size
) { }
