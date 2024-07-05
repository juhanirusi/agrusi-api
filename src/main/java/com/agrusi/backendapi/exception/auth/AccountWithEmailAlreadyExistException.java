package com.agrusi.backendapi.exception.auth;

import com.agrusi.backendapi.exception.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccountWithEmailAlreadyExistException extends BaseCustomException {

    public AccountWithEmailAlreadyExistException(final String email) {
        super(
                HttpStatus.CONFLICT,
                "Account creation failed.",
                "Account with email address '" + email + "' already exists."
        );
    }

    public AccountWithEmailAlreadyExistException() {
        super(
                HttpStatus.CONFLICT,
                "Account creation failed.",
                "Account with the provided email address already exists."
        );
    }
}
