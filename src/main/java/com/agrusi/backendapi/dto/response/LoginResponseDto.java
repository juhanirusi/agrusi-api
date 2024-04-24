package com.agrusi.backendapi.dto.response;

public record LoginResponseDto (
        String email,
        String token
) { }
