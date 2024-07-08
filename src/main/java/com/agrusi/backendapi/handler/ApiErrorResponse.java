package com.agrusi.backendapi.handler;

import java.util.List;

public record ApiErrorResponse(
        String status,
        String message,
        List<ApiErrorMessage> errors
) {}
