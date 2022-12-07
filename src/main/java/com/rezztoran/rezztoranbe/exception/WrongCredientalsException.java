package com.rezztoran.rezztoranbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongCredientalsException extends RuntimeException {
    public WrongCredientalsException(String message) {
        super(message);
    }
}
