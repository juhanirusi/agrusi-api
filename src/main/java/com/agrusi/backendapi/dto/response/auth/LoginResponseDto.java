package com.agrusi.backendapi.dto.response.auth;

public record LoginResponseDto(
        String email,
        String token
) { }
