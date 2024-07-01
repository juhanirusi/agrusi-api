package com.agrusi.backendapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseHandler {

    public static <T> ResponseEntity<ApiSuccessResponse<T>> generateSuccessResponse(
            HttpStatus httpStatus, String message, T data
    ) {
        ApiSuccessResponse<T> response = new ApiSuccessResponse<>("success", message, data);
        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<ApiErrorResponse> generateErrorResponse(
            HttpStatus httpStatus, String message, String errorDetail
    ) {
        ApiErrorMessage error = new ApiErrorMessage(errorDetail);
        ApiErrorResponse response = new ApiErrorResponse("error", message, List.of(error));
        return new ResponseEntity<>(response, httpStatus);
    }
}
