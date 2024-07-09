package com.agrusi.backendapi.handler;

public record ApiSuccessResponse<T>(
        String status,
        String message,
        T data
) { }
