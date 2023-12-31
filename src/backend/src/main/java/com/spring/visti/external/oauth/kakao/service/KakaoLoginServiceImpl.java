package com.spring.visti.external.oauth.kakao.service;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.external.oauth.kakao.dto.KakaoTokenDTO;
import com.spring.visti.external.oauth.kakao.dto.KakaoUserProfileDTO;
import com.spring.visti.external.oauth.service.LoginService;
import com.spring.visti.external.oauth.service.OAuthDTOConverter;
import com.spring.visti.external.oauth.service.OAuthService;
import com.spring.visti.global.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:application-oauth.properties")
public class KakaoLoginServiceImpl implements OAuthService {


    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenProviderUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;

    private final OAuthDTOConverter<KakaoTokenDTO> converter;

    @Override
    public BaseResponseDTO<TokenDTO> processOAuthLogin(Map<String, String> OAuthParams) {
        KakaoTokenDTO kakaoToken = converter.convertToDto(OAuthParams);

        assert kakaoToken != null;
        Member member = getKakaoMember(kakaoToken);

        return loginService.SocialLogin(member);
    }

    public Member getKakaoMember(KakaoTokenDTO kakaoTokenDTO) {

        String kakaoAccessToken = kakaoTokenDTO.getAccessToken(); // 액세스 토큰 추출 로직 구현 필요

        KakaoUserProfileDTO kakaoUserInfo = getUserInfo(kakaoAccessToken);

        System.out.println(kakaoUserInfo);
        String encryptedPassword = passwordEncoder.encode(MemberType.KAKAO + kakaoUserInfo.getId());

        return memberRepository.findByEmail(MemberType.KAKAO + kakaoUserInfo.getId())
                .orElseGet(() -> {
                    Member newUser = Member.builder()
                            .email(MemberType.KAKAO + kakaoUserInfo.getId())
                            .password(encryptedPassword)
                            .nickname(kakaoUserInfo.getProperties().getNickname())
                            .profilePath(kakaoUserInfo.getProperties().getProfileImageUrl())
                            .role(Role.USER)
                            .memberType(MemberType.KAKAO)
                            .build();

                    // 필요한 정보 세팅...
                    return memberRepository.save(newUser);
                });
    }

    private KakaoUserProfileDTO getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserProfileDTO> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, entity, KakaoUserProfileDTO.class);

        return response.getBody();
    }
}
