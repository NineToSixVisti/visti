package com.spring.visti.common.items;

import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
public class Member {
     public final static String email = "tncks097@naver.com";
     public final static String accountPassWord = "Qwert1234!";
     public final static String nickname = "테스트 닉네임";
     public static String profilePath;
     public static String refreshToken;
     public static Boolean status = true;
     final static Role role = Role.USER;
     final static Integer dailyStoryCount = 0;
     final static MemberType memberType = MemberType.SOCIAL;

    public static MemberJoinDTO 회원가입_정보작성() {
        return MemberJoinDTO.builder()
                .nickname(nickname)
                .email(email)
                .password(accountPassWord)
                .build();
    }

    public static ExtractableResponse<Response> 회원가입요청(final MemberJoinDTO request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/member/signup")
                .then()
                .log().all().extract();
    }

    public static MemberLoginDTO 로그인_정보_작성() {
        return MemberLoginDTO.builder()
                .email(email)
                .password(accountPassWord)
                .build();
    }
    
    public static ExtractableResponse<Response> 로그인(final MemberLoginDTO request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/member/signin")
                .then()
                .log().all().extract();
    }
    
    public static MemberJoinDTO 회원가입_정보작성(String nickname, String email, String accountPassWord) {
        return MemberJoinDTO.builder()
                .nickname(nickname)
                .email(email)
                .password(accountPassWord)
                .build();
    }



}
