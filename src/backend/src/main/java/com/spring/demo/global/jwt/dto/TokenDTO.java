package com.spring.demo.global.jwt.dto;

import com.spring.demo.global.jwt.constant.GrantType;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class TokenDTO {
    private GrantType grantType;
    private String accessToken;
    private Date accessTokenExpireTime;

    private String refreshToken;
    private Date refreshTokenExpireTime;

}
