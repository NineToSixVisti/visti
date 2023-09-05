package com.spring.demo.utils.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(final String message) {
        super(message);
    }
}
