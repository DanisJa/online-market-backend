package com.onlinemarket.core.exceptions.ApiExceptions;

import com.onlinemarket.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class ConflictException extends GeneralException {
    public ConflictException() {super(HttpStatus.CONFLICT.value());}
    public ConflictException(String message) {super(HttpStatus.CONFLICT.value(), message);}
}
