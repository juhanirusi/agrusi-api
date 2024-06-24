package com.agrusi.backendapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class ResponseHandler {

    public static ResponseEntity<Object> generateSuccessResponse(
            HttpStatus httpStatus, String message, Object data
    ) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", "success");

        if (message != null) {
            map.put("message", message);
        }

        if (data != null) {
            map.put("data", data);
        }

        return new ResponseEntity<>(map, httpStatus);
    }

    public static ResponseEntity<Object> generateErrorResponse(
            HttpStatus httpStatus, String message, Object error
    ) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", "error");

        if (message != null) {
            map.put("message", message);
        }

        if (error != null) {
            List<Object> errors = new ArrayList<>();
            errors.add(error);
            map.put("errors", errors);
        }

        return new ResponseEntity<>(map, httpStatus);
    }
}
