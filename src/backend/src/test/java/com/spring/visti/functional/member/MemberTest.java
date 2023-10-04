package com.spring.visti.functional.member;

import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.common.AcceptanceTest;
import com.spring.visti.common.items.Member;
import com.spring.visti.domain.member.dto.RequestDTO.MemberInformDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class MemberTest extends AcceptanceTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MemberTest.class);
    private String accessToken;

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입_성공_테스트() {
        // 1. 회원 가입 데이터 준비
        MemberJoinDTO request = Member.회원가입_정보작성();

        // 2. 회원 가입 요청
        ExtractableResponse<Response> response = Member.회원가입요청(request);

        response.statusCode();

        log.info("회원가입_성공_테스트");
    }

    @Test
    public void 이메일_중복확인_중복이_없는경우(){
        // 1. 이메일 중복 확인
        MemberInformDTO request = MemberInformDTO.builder()
                .email("tncks007@naver.com")
                .build();

        // 2. 이메일 중복 확인 요청
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                .when()
                    .post("/api/member/verify-member")
                .then()
                .log().all().extract();
    }

    @Test
    public void 이메일_중복확인_정상적인_이메일이_아닐경우(){
        // 1. 이메일 중복 확인
        MemberInformDTO request = MemberInformDTO.builder()
                .email("tncks007naver.com")
                .build();
        // 2. 이메일 중복 확인
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/member/verify-member")
                .then()
                .log().all().extract();
    }

    @Test
    public void 이메일_중복확인_중복이_있는_경우(){
        // 1. 중복이 될 이메일 작성
        MemberJoinDTO defaultMember = Member.회원가입_정보작성("nickname_example", "tncks007@naver.com", "testPW123@");
        memberService.signUp(defaultMember);

        MemberInformDTO request = MemberInformDTO.builder()
                .email("tncks007@naver.com")
                .build();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/member/verify-member")
                .then()
                .log().all().extract();
    }

    @Test
    public void 회원가입_인증번호_전송_및_인증_성공() throws JSONException {
        // 이메일 인증번호 전송
        ExtractableResponse<Response> response1 = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("email", "tncks097@naver.com")
                    .param("type", "certification")
                .when()
                    .get("/api/member/sendmail")
                .then()
                    .log().all().extract();

        // 이메일로 데이터 받아오기
        JSONObject firstMessage = 메시지_받아오기();

        // 인증번호 가져오기
        String authCode = 인증번호_가져오기(firstMessage);

        // 인증번호를 통해 인증 진행
        AuthDTO authData = AuthDTO.builder()
                .email("tncks097@naver.com")
                .authNum(authCode)
                .type("certification")
                .build();

        // 이메일 인증번호 전송
        ExtractableResponse<Response> response2 = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(authData)
                .when()
                .post("/api/member/verify-authnum")
                .then()
                .log().all().extract();
    }

    @Test
    public void 회원가입_인증번호_전송_및_인증_실패() throws JSONException {
        // 이메일 인증번호 전송
        ExtractableResponse<Response> response1 = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("email", "tncks097@naver.com")
                .param("type", "certification")
                .when()
                .get("/api/member/sendmail")
                .then()
                .log().all().extract();

        // 이메일로 데이터 받아오기
        JSONObject firstMessage = 메시지_받아오기();

        // 인증번호 가져오기
        String authCode = 인증번호_가져오기(firstMessage);

        // 인증번호를 통해 인증 진행
        AuthDTO authData = AuthDTO.builder()
                .email("tncks097@naver.com")
                .authNum("authCode")
                .type("certification")
                .build();

        // 이메일 인증번호 전송
        ExtractableResponse<Response> response2 = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(authData)
                .when()
                .post("/api/member/verify-authnum")
                .then()
                .log().all().extract();
    }

    @Test
    public void 회원가입_실패_테스트_이메일중복() {
        // 1. 중복이 될 이메일 작성
        MemberJoinDTO defaultMember = Member.회원가입_정보작성("nickname_example", "test@email.com", "testPW123@");
        memberService.signUp(defaultMember);

        // 2. 중복 이메일 작성
        MemberJoinDTO request = Member.회원가입_정보작성("mergedTest", "test@email.com", "testPW1");

        // 3. 회원 가입 요청
        ExtractableResponse<Response> response = Member.회원가입요청(request);
    }

    @Test
    public void 회원가입_실패_테스트_이메일형식아님(){
        // 1. 이메일 형식이 아님
        MemberJoinDTO request = Member.회원가입_정보작성("mergedTest", "tesemail.com", "testPW1");

        // 2. 회원 가입 요청
        ExtractableResponse<Response> response = Member.회원가입요청(request);
    }

    @Test
    public void 회원가입_실패_테스트_비밀번호_형식이_다름(){
        // 1. 비밀번호가 8자리가 아님
        MemberJoinDTO NoLength8 = Member.회원가입_정보작성("mergedTest", "test@email.com", "tstPW1@");
        // 2. 회원 가입 요청
        ExtractableResponse<Response> response1 = Member.회원가입요청(NoLength8);

        // 1. 비밀번호가 대문자가 없음
        MemberJoinDTO NoCap = Member.회원가입_정보작성("mergedTest", "test@email.com", "qwer1234@");
        // 2. 회원 가입 요청
        ExtractableResponse<Response> response2 = Member.회원가입요청(NoCap);

        // 1. 비밀번호가 특수문자가 없음
        MemberJoinDTO NoExclamation = Member.회원가입_정보작성("mergedTest", "test@email.com", "Qwer1234");
        // 2. 회원 가입 요청
        ExtractableResponse<Response> response3 = Member.회원가입요청(NoExclamation);
    }

    @Test
    public void 로그인후_ACCESS_TOKEN_만료_뒤_재접속(){
        System.out.println("== Set Up == 로그인 기본정보 작성");
        MemberJoinDTO requestSignUp = Member.회원가입_정보작성();
        ExtractableResponse<Response> responseSignUp = Member.회원가입요청(requestSignUp);

        MemberLoginDTO requestSignIn = Member.로그인_정보_작성();
        ExtractableResponse<Response> responseSignIn = Member.로그인(requestSignIn);

        accessToken = responseSignIn.jsonPath().getString("detail.accessToken");

        try {
            System.out.println("Waiting for the token to expire...");
            Thread.sleep((30 * 60 + 10) * 1000); // 30분 1초 대기 (단위: 밀리초)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .when()
                .get("/api/member/mypage")
                .then()
                .log().all().extract();

    }

    private JSONObject 메시지_받아오기() throws JSONException {
        // 이메일로 데이터 받아오기
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String mailhogApiUrl = "http://localhost:8025/api/v2/messages";
        ResponseEntity<String> emailResponse = restTemplate.getForEntity(mailhogApiUrl, String.class);
        String jsonResponse = emailResponse.getBody();

        // 첫번째 이메일 메시지 받아오기
        JSONObject responseObject = new JSONObject(jsonResponse);
        JSONArray itemsArray = responseObject.getJSONArray("items");
        return itemsArray.getJSONObject(0);
    }

    private String 인증번호_가져오기(JSONObject firstMessage){
        try {
            JSONObject rawData = firstMessage.getJSONObject("Content");

            String encodedHtmlBody = rawData.getString("Body");

            // 본문 HTML에서 인증 코드 추출하기
            Document mailDoc = Jsoup.parse(encodedHtmlBody);

            Elements codeElements = mailDoc.select("div[style*=font-size:130%]");

            if (codeElements.isEmpty()) {
                return "null";  // 인증 코드가 없습니다.
            }

            return Objects.requireNonNull(codeElements.first()).text();
        } catch (JSONException e) {
            e.printStackTrace();
            return "null";  // JSON 파싱 오류
        }
    }



}