package com.spring.demo.utils.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }
}
