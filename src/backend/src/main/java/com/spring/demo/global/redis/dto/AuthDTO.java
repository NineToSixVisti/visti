package com.spring.demo.global.redis.dto;

import lombok.Getter;

@Getter
public class AuthDTO {
    private String email;
    private String authNum;
    private String type;
}
