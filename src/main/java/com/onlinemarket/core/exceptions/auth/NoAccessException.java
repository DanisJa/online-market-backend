package com.onlinemarket.core.exceptions.auth;

import com.onlinemarket.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class NoAccessException extends GeneralException {
    public NoAccessException() {super(HttpStatus.FORBIDDEN.value());}
    public NoAccessException(String message) {super(HttpStatus.FORBIDDEN.value(), message);}
}
