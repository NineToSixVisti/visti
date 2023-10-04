package com.spring.visti.external.oauth.naver.service;

import com.spring.visti.external.oauth.naver.dto.NaverTokenDTO;
import com.spring.visti.external.oauth.service.OAuthDTOConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NaverDTOConverter implements OAuthDTOConverter<NaverTokenDTO> {
    @Override
    public NaverTokenDTO convertToDto(Map<String, String> params) {
        String accessToken = params.get("accessToken");
        String tokenType = params.get("tokenType");
        String refreshToken = params.get("refreshToken");

        return new NaverTokenDTO(accessToken, tokenType, refreshToken);
    }
}