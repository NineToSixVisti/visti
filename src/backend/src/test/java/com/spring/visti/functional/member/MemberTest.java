package com.spring.visti.functional.member;

import com.spring.visti.common.AcceptanceTest;
import com.spring.visti.common.items.Member;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.global.config.SecurityConfig;
import groovy.util.logging.Slf4j;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
public class MemberTest extends AcceptanceTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MemberTest.class);
    @Test
    public void 회원가입_성공_테스트() {
        // 1. 회원 가입 데이터 준비
        MemberJoinDTO request = Member.회원가입_정보작성();

        // 2. 회원 가입 요청
        ExtractableResponse<Response> response = Member.회원가입요청(request);

        // 4. Response 제공

        // 응답 본문도 검증 가능, 예: assertThat(response.body().jsonPath().getString("key")).isEqualTo("expectedValue");
    }
}