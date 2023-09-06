package com.spring.visti.external.oauth.service;

import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.external.oauth.kakao.service.KakaoLoginServiceImpl;
import com.spring.visti.external.oauth.naver.service.NaverLoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceFactory {

    private final KakaoLoginServiceImpl kakaoService;

    private final NaverLoginServiceImpl naverService;

    public OAuthService getService(String provider) {

        String Provider = provider.toUpperCase();

        if (Provider.equals(MemberType.KAKAO.name())) {
            return kakaoService;
        } else if (Provider.equals(MemberType.NAVER.name())) {
            return naverService;
        }

        throw new IllegalArgumentException("Invalid provider - check provider again");
    }
}