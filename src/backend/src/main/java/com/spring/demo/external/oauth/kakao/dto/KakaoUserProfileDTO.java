package com.spring.demo.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserProfileDTO {

    private String id;

    @JsonProperty("connected_at")
    private String connectedAt;

    private KakaoProfileProperties properties;

    // Getter, Setter 및 필요한 메서드 추가

    @Getter
    public static class KakaoProfileProperties {
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImageUrl;

        // Getter, Setter 및 필요한 메서드 추가
    }
}