package com.agrusi.backendapi.dto;

public record ApiErrorResponse(
        int errorCode,
        String description
) { }
