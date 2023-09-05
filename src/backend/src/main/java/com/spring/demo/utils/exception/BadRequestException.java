package com.spring.demo.utils.exception;


public class BadRequestException extends BusinessException {

    public BadRequestException(final String message) {
        super(message);
    }

}
