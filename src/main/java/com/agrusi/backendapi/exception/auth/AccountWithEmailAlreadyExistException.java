package com.agrusi.backendapi.exception.auth;

public class AccountWithEmailAlreadyExistException extends RuntimeException {

    public AccountWithEmailAlreadyExistException(final String message) {
        super(message);
    }
}
