package com.onlinemarket.core.exceptions.general;

import com.onlinemarket.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends GeneralException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
