package com.spring.visti.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode error;
    private final HttpStatus status;

    public ApiException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
        this.status = error.getStatus();
    }

    public ErrorCode getCode() {
        return error;
    }

}
