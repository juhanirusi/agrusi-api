package com.agrusi.backendapi.exception;

import java.util.Date;
import java.util.List;

public record ApiError(Date timestamp, int statusCode, String message, String path, List<?> errors) { }
