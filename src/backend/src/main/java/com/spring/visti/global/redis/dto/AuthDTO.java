package com.spring.visti.global.redis.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthDTO {
    private String email;
    private String authNum;
    private String type;

    @Builder
    public AuthDTO(String email, String authNum, String type){
        this.email = email;
        this.authNum = authNum;
        this.type = type;
    }
}
