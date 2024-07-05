package com.agrusi.backendapi.exception;

import com.agrusi.backendapi.handler.ApiErrorResponse;
import com.agrusi.backendapi.handler.ResponseHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Our overridden "handleMethodArgumentNotValid" exception to throw
     * more user-friendly and useful validation error responses to the user.
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        Set<String> fieldsAlreadyInResponse = new HashSet<>();

        List<Map<String, String>> errorDetails = new ArrayList<>();

        for (FieldError error : fieldErrors) {

            /*
            If a field has multiple validation errors, only tell about one of them,
            so we can avoid 3 different error messages telling the same thing for
            one field, for example.
            */

            if (!fieldsAlreadyInResponse.contains(error.getField())) {
                Map<String, String> errorMap = new LinkedHashMap<>();
                errorMap.putIfAbsent("field", error.getField());
                errorMap.putIfAbsent("reason", error.getDefaultMessage());
                errorDetails.add(errorMap);
            }

            fieldsAlreadyInResponse.add(error.getField());
        }

        ApiError apiError = new ApiError(
                "error",
                "Validation failed",
                errorDetails
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Our overridden "handleHttpMessageNotReadable" exception to throw
     * more user-friendly and useful error responses to the user when they
     * accidentally input a JSON body that contains parsing errors.
     */

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<Map<String, String>> errors = new ArrayList<>();
        Map<String, String> error = new HashMap<>();
        error.put(
                "message",
                "The request body is not readable, is empty or contains invalid data."
        );
        errors.add(error);

        ApiError apiError = new ApiError(
                "error",
                "JSON parse error",
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Our ExceptionHandler that will throw all the custom exceptions we've
     * made that extend our "BaseCustomException" abstract class that is
     * (as the name suggests) a base model for our custom-made exceptions
     */

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseCustomException(
            BaseCustomException exception
    ) {
        return ResponseHandler.generateErrorResponse(
                exception.getStatus(),
                exception.getGenericMessage(),
                exception.getDetailMessage()
        );
    }

    /**
     * We can't still use this ExceptionHandler that catches every other
     * exception that we've configured in our program, because this handlers
     * hierarchy is before our "JsonAuthenticationEntryPoint", hence, authentication
     * exceptions will get thrown by this handler instead
     * of the "JsonAuthenticationEntryPoint"
     */

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiErrorResponse> handleGenericException(
//            Exception exception
//    ) {
//        return ResponseHandler.generateErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred",
//                exception.getMessage()
//        );
//    }
}
