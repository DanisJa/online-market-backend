package com.onlinemarket.core.exceptions.auth;

import com.onlinemarket.core.exceptions.GeneralException;
import com.onlinemarket.core.exceptions.general.InvalidParametersException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends GeneralException {
    public InvalidCredentialsException() {super(HttpStatus.UNAUTHORIZED.value());}
    public InvalidCredentialsException(String message) {super(HttpStatus.UNAUTHORIZED.value(), message);}
}
