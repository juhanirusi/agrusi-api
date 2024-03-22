package com.agrusi.backendapi.exception.auth;

import com.agrusi.backendapi.exception.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class AuthGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
    * Our overridden "handleMethodArgumentNotValid" exception to throw
     * more user-friendly and useful validation error responses to the user
    */

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException exception,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request
//    ) {
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//
//        List<Map<String, String>> errorDetails = new ArrayList<>();
//
//        for (FieldError error : fieldErrors) {
//            Map<String, String> errorMap = new LinkedHashMap<>();
//            errorMap.put("field", error.getField());
//            errorMap.put("reason", error.getDefaultMessage());
//            errorDetails.add(errorMap);
//        }
//
//        Map<String, Object> errorResponse = new LinkedHashMap<>();
//
//        errorResponse.put("timestamp", new Date());
//        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
//        errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
//        errorResponse.put("message", "Validation failed");
//        errorResponse.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());
//        errorResponse.put("errors", errorDetails);
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<Map<String, String>> errorDetails = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            Map<String, String> errorMap = new LinkedHashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("reason", error.getDefaultMessage());
            errorDetails.add(errorMap);
        }

        ApiError apiError = new ApiError(
                new Date(),
                status.value(),
                "Validation failed",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                errorDetails
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
