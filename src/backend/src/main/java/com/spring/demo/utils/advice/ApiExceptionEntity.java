package com.spring.demo.utils.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    private final Integer statusCode;

    @Builder
    public ApiExceptionEntity(HttpStatus status, String errorCode, String message, Integer statusCode){
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }
}
