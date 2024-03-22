package com.agrusi.backendapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(
            HttpStatus status, String message, Object responseObj
    ) {
        Map<String, Object> map = new LinkedHashMap<>();

        try {
            map.put("timestamp", new Date());
            map.put("statusCode", status.value());
            map.put("message", message);

            if (responseObj != null) {
                map.put("data", responseObj);
            }

            return new ResponseEntity<>(map, status);

        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("message", e.getMessage());

            if (responseObj != null) {
                map.put("data", responseObj);
            }

            return new ResponseEntity<>(map, status);
        }
    }
}
