package com.spring.demo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponseDTO<T> {

    private String message;
    private int statusCode;
    private T detail;

    public BaseResponseDTO(String Message, int statusCode) {
        this.message = Message;
        this.statusCode = statusCode;
    }

    public BaseResponseDTO(String Message, int statusCode, T detail) {
        this.message = Message;
        this.statusCode = statusCode;
        this.detail = detail;
    }
}
