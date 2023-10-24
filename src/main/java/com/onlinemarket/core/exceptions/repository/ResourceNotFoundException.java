package com.onlinemarket.core.exceptions.repository;

import com.onlinemarket.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends GeneralException {
    public ResourceNotFoundException(){super(HttpStatus.NO_CONTENT.value());}
    public ResourceNotFoundException(String message){super(HttpStatus.NO_CONTENT.value(), message);}
}
