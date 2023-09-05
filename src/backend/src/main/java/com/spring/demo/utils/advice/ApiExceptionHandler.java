package com.spring.demo.utils.advice;

import com.spring.demo.utils.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        // 에러 로깅
        log.error("API Error occurred. URL: {}, Message: {}", request.getRequestURI(), e.getMessage(), e);

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .status(e.getError().getStatus())
                        .errorCode(e.getError().getCode())
                        .message(e.getError().getMessage())
                        .statusCode(e.getError().getStatus().value())
                        .build());
    }
}
