package com.spring.visti.functional.story;

import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.common.AcceptanceTest;
import com.spring.visti.common.items.Member;
import com.spring.visti.common.items.Story;
import com.spring.visti.common.items.StoryBox;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.functional.storybox.StoryBoxTest;
import com.spring.visti.utils.exception.ApiException;
import groovy.io.FileType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static com.spring.visti.utils.exception.ErrorCode.NO_STORY_ERROR;

public class StoryTest extends AcceptanceTest {

    @Autowired
    private MemberService memberService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StoryTest.class);

    // 토큰 관련 전역 변수 추가
    private String accessToken;

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
    }

    @Test
    public void 스토리_생성_테스트(){
        String storyBoxId = _스토리박스_접근();
        log.info(storyBoxId);

        StoryBuildDTO storyBuildDTO = StoryBuildDTO.builder()
                .storyBoxId(storyBoxId)
                .mainFileType(StoryType.IMAGE)
                .build();
        File fileToUpload = fileLoader("static/testImg1.jpg");


        // 스토리 생성
        RestAssured.given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .multiPart("storyInfo", storyBuildDTO, "application/json")  // 스토리 정보를 멀티파트로 추가
                .multiPart("file", fileToUpload)   // 파일을 멀티파트로 추가
                .when()
                .post("/api/story/create")
                .then()
                .log().all().extract();

    }

    @Test
    public void 스토리_조회_테스트(){

    }

    @Test
    public void 스토리_삭제_테스트(){

    }
    

    private void 스토리박스_생성_테스트(){

        StoryBoxBuildDTO storyBoxInfo = StoryBox.스토리박스_생성();
        File fileToUpload = fileLoader("static/testBoxImg1.jpg"); // 여기에 실제 업로드할 파일의 경로를 지정해주세요.

        // 스토리박스 생성
        RestAssured.given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Access_Token", accessToken)  // 토큰 정보를 헤더에 추가
                .multiPart("storyBoxInfo", storyBoxInfo, "application/json")  // 스토리 정보를 멀티파트로 추가
                .multiPart("file", fileToUpload)   // 파일을 멀티파트로 추가
                .when()
                .post("/api/story-box/create")
                .then()
                .log().all().extract();
    }

    private String _스토리박스_접근(){
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

        return response.path("detail.content[0].encryptedId");
    }


    private File fileLoader(String imgPath){
        try{
            URL resourceUrl = getClass().getClassLoader().getResource(imgPath);
            System.out.println("Trying to load file from path: " + imgPath);
            File fileToUpload;
            if (resourceUrl != null) {
                fileToUpload = new File(resourceUrl.toURI());
                return fileToUpload;
            } else {
                throw new ApiException(NO_STORY_ERROR);
            }
        }catch (Error | URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
}
