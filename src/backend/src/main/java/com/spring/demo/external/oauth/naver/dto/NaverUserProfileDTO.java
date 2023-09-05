package com.spring.demo.external.oauth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserProfileDTO {

    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    private Response response;

    @Getter
    @NoArgsConstructor
    public static class Response {
        private String id;  // 동일인 식별 정보

        private String nickname;  // 사용자 별명

        private String name;  // 사용자 이름

        private String email;  // 사용자 메일 주소

        @JsonProperty("profile_image")
        private String profileImageUrl;  // 사용자 프로필 사진 URL

    }
}