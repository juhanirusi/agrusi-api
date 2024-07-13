package com.agrusi.backendapi.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseCustomException extends RuntimeException {

    private final HttpStatus status;
    private final String genericMessage;
    private final String detailMessage;

    protected BaseCustomException(
            HttpStatus status,
            String genericMessage,
            String detailMessage
    ) {
        super(detailMessage);
        this.status = status;
        this.genericMessage = genericMessage;
        this.detailMessage = detailMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getGenericMessage() {
        return genericMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
