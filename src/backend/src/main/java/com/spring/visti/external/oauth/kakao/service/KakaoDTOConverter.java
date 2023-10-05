package com.spring.visti.external.oauth.kakao.service;

import com.spring.visti.external.oauth.kakao.dto.KakaoTokenDTO;
import com.spring.visti.external.oauth.service.OAuthDTOConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KakaoDTOConverter implements OAuthDTOConverter<KakaoTokenDTO> {
    @Override
    public KakaoTokenDTO convertToDto(Map<String, String> params) {
        String accessToken = params.get("accessToken");
        String tokenType = params.get("tokenType");
        String refreshToken = params.get("refreshToken");

        return new KakaoTokenDTO(accessToken, tokenType, refreshToken);
    }
}