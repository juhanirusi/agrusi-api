package com.agrusi.backendapi.exception.auth;

public class AccountWithEmailAlreadyExistException extends RuntimeException {

    public AccountWithEmailAlreadyExistException(final String email) {
        super("Account with email '" + email + "' already exists");
    }

    public AccountWithEmailAlreadyExistException() {
        super("Account with this email already exists");
    }
}
