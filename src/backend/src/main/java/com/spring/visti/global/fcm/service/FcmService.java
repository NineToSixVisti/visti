package com.spring.visti.global.fcm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.global.fcm.dto.FCMMessageDTO;
import com.spring.visti.global.fcm.dto.FCMToMemberDTO;
import com.spring.visti.utils.exception.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.spring.visti.utils.exception.ErrorCode.FAILED_TO_SEND_MESSAGE;
import static com.spring.visti.utils.exception.ErrorCode.NO_MEMBER_ERROR;

@Service
@Slf4j
@AllArgsConstructor
public class FcmService {


    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    private final static String FIREBASE_ALARM_SEND_API_URI="https://fcm.googleapis.com/v1/projects/visti-e32d0/messages:send";
    private final static String FIREBASE_PATH="visti-e32d0-firebase-adminsdk-nefcc-1a705a8042.json";
    @Transactional
    public BaseResponseDTO<String> linkFCMTokenToMember(FCMToMemberDTO fcmToMemberDTO, String email) {

        String fireBaseToken = fcmToMemberDTO.getFireBaseToken();
        Member member = getMember(email);

        member.updateFCMToken(fireBaseToken);
        memberRepository.save(member);

        return new BaseResponseDTO<String>("FCM 토큰이 저장 완료되었습니다.", 200);
    }

    private String getAccessToken() throws IOException {
        // firebase로 부터 access token을 가져온다.

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_PATH).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * makeMessage : 알림 파라미터들을 FCM이 요구하는 body 형태로 가공한다.
     * @param targetToken : firebase token
     * @param title : 알림 제목
     * @param body : 알림 내용
     * @return
     * */
    public String makeMessage(
            String targetToken, String title, String body, String name, String description
    ) throws JsonProcessingException {

        FCMMessageDTO fcmMessage = FCMMessageDTO.builder()
                .message(
                        FCMMessageDTO.Message.builder()
                                .token(targetToken)
                                .notification(
                                        FCMMessageDTO.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .build()
                                )
                                .data(
                                        FCMMessageDTO.Data.builder()
                                                .name(name)
                                                .description(description)
                                                .build()
                                )
                                .build()
                )
                .validateOnly(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);

    }

    /**
     * 알림 푸쉬를 보내는 역할을 하는 메서드
     * @param targetToken : 푸쉬 알림을 받을 클라이언트 앱의 식별 토큰
     * */
    public void sendMessageTo(
            String targetToken, String title, String body, String id, String isEnd
    ) throws IOException{

        String message = makeMessage(targetToken, title, body, id, isEnd);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(FIREBASE_ALARM_SEND_API_URI)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            log.info(response.body().string());
        }catch (Exception e){
            throw new ApiException(FAILED_TO_SEND_MESSAGE);
        }
    }

    private Member getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()) { throw new ApiException(NO_MEMBER_ERROR); }
        return optionalMember.get();
    }


}