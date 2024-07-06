package com.agrusi.backendapi.exception.account;

import com.agrusi.backendapi.exception.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountWithPublicIdNotFoundException extends BaseCustomException {

    public AccountWithPublicIdNotFoundException(final UUID publicId) {
        super(
                HttpStatus.NOT_FOUND,
                "Account not found.",
                "Account with public ID '" + publicId + "' wasn't found."
        );
    }

    public AccountWithPublicIdNotFoundException() {
        super(
                HttpStatus.NOT_FOUND,
                "Account not found.",
                "Account with the provided public ID wasn't found."
        );
    }
}
