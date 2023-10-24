package com.onlinemarket.core.exceptions.general;

import com.onlinemarket.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidParametersException extends GeneralException {
    public InvalidParametersException(){super(HttpStatus.BAD_REQUEST.value());}
    public InvalidParametersException(String message){super(HttpStatus.BAD_REQUEST.value(), message);}
}
