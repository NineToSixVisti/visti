package com.spring.visti.functional.storybox;

import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.common.AcceptanceTest;
import com.spring.visti.common.items.Member;
import com.spring.visti.common.items.StoryBox;
import com.spring.visti.domain.member.dto.RequestDTO.MemberInformDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.functional.member.MemberTest;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Slf4j
public class StoryBoxTest extends AcceptanceTest {

    @Autowired
    private MemberService memberService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StoryBoxTest.class);

    // 토큰 관련 전역 변수 추가
    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void setUpInfo() {
        // 사용자가 없기 때문에 생성을 해야함.
        // 1. 회원 가입 데이터 준비
        System.out.println("== Set Up == 로그인 기본정보 작성");
        MemberJoinDTO requestSignUp = Member.회원가입_정보작성();
        ExtractableResponse<Response> responseSignUp = Member.회원가입요청(requestSignUp);

        MemberLoginDTO requestSignIn = Member.로그인_정보_작성();
        ExtractableResponse<Response> responseSignIn = Member.로그인(requestSignIn);

        accessToken = responseSignIn.jsonPath().getString("detail.accessToken");
        refreshToken = responseSignIn.jsonPath().getString("detail.refreshToken");
    }

    @Test
    public void 스토리박스_생성_테스트(){

        StoryBoxBuildDTO request = StoryBox.스토리박스_생성();

        // 2. 스토리박스 생성
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                    .body(request)
                .when()
                    .post("/api/story-box/create")
                .then()
                    .log().all().extract();
    }
    @Test
    public void 스토리박스_접근(){
        스토리박스_생성();

        // 2. 스토리박스 조회
        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .when()
                .get("/api/story-box/mystorybox")
                .then()
                .log().all().extract();

    }

    @Test
    public void 스토리박스_정보_조회(){
        Integer storyBoxId = _스토리박스_접근();

        // 2. 스토리박스 정보 조회
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/info")
                .then()
                .log().all().extract();

        // 2. 스토리박스 스토리 리스트 조회
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/story-list")
                .then()
                .log().all().extract();

        // 2. 스토리박스 맴버 조회
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/members")
                .then()
                .log().all().extract();

        // 2. 스토리박스 상세정보 조회
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/detail")
                .then()
                .log().all().extract();

    }

    @Test
    public void 스토리박스_링크_받기(){
        Integer storyBoxId = _스토리박스_접근();
        // 2. 스토리박스 상세정보 조회
        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/generate")
                .then()
                .log().all().extract();

    }

    @Test
    public void 스토리_박스_링크_사용(){
        String shortUrl = _스토리박스_링크_받기();
        
        log.info("URL 정보" + shortUrl);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)
                .when()
                .get(shortUrl)
                .then()
                .log().all().extract();
    }

    @Test
    public void 스토리박스들_조회(){
        스토리박스_생성();

        // 2. 스토리박스 조회
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                    .param("page", 1)
                .when()
                    .get("/api/story-box/mystorybox")
                .then()
                    .log().all().extract();
    }


    public void 스토리박스_생성(){

        StoryBoxBuildDTO request = StoryBox.스토리박스_생성();

        // 2. 스토리박스 생성
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .body(request)
                .when()
                .post("/api/story-box/create")
                .then()
                .extract();
    }

    public Integer _스토리박스_접근(){
        스토리박스_생성_테스트();

        // 2. 스토리박스 조회
        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .when()
                .get("/api/story-box/mystorybox")
                .then()
                .extract();

        return response.path("detail.content[0].id");
    }

    public String _스토리박스_링크_받기(){
        Integer storyBoxId = _스토리박스_접근();
        // 2. 스토리박스 상세정보 조회
        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .param("page", 0)
                .pathParam("storyBoxId", storyBoxId)
                .when()
                .get("/api/story-box/{storyBoxId}/generate")
                .then()
                .extract();

        return response.path("detail");
    }

}
