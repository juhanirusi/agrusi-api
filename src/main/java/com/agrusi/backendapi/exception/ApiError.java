package com.agrusi.backendapi.exception;

import java.util.List;

public record ApiError(
        String status,
        String message,
        List<?> errors
) { }
