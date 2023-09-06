package com.spring.visti.external.oauth.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.global.jwt.dto.TokenDTO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface OAuthService {
    BaseResponseDTO<TokenDTO> processOAuthLogin(Map<String, String> params);

    default String encodeStringToUTF8(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}