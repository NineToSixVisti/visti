package com.spring.visti.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Authentication Part
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "AUTH001", "비밀번호의 형식이 잘못되었습니다."),
    EMAIL_INPUT_ERROR(HttpStatus.BAD_REQUEST, "AUTH002", "이메일 인증 번호가 잘못되었습니다."),
    REGISTER_DUPLICATED_EMAIL(HttpStatus.CONFLICT, "AUTH003", "이미 가입되어 있는 유저입니다."),
    LOGIN_INFO_ERROR(HttpStatus.UNAUTHORIZED, "AUTH004", "로그인 정보가 일치하지 않습니다."),
    NO_MEMBER_ERROR(HttpStatus.NOT_FOUND, "AUTH005", "유저 정보가 없습니다."),
    NO_AUTHORIZE_ERROR(HttpStatus.NOT_FOUND, "AUTH005", "접근 권한이 없습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "AUTH006", "이메일 형식이 잘못되었습니다."),
    NOT_VALID_TYPE4SEND_MAIL(HttpStatus.BAD_REQUEST, "AUTH006", "이메일 보내기에 적절한 타입이 값이 아닙니다."),

    // JWT Notification Part
    NO_TOKEN_HEADER(HttpStatus.UNAUTHORIZED, "AUTH010", "토큰이 존재하지 않습니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "AUTH011", "잘못된 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH012", "만료된 JWT 토큰입니다."),
    JWT_NOT_SUPPORT(HttpStatus.FORBIDDEN, "AUTH013", "지원되지 않는 JWT 토큰입니다."),
    JWT_ERROR(HttpStatus.BAD_REQUEST, "AUTH014", "JWT 토큰이 잘못되었습니다."),
    NO_AUTHORIZE(HttpStatus.FORBIDDEN, "AUTH015", "권한 정보가 없는 토큰입니다."),
    NO_RT_IN_DB(HttpStatus.BAD_REQUEST, "AUTH016", "해당 리프레쉬 토큰이 DB에 존재하지 않습니다."),

    // Member Part
    PW_MATCH_ERROR(HttpStatus.BAD_REQUEST, "MEMBER001", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_FRIEND(HttpStatus.CONFLICT, "MEMBER002", "이미 친구 요청이 된 관계거나, 친구 사이입니다."),
    NO_SHOW_FRIENDS(HttpStatus.NOT_FOUND, "MEMBER003", "친구 목록이 존재하지 않습니다."),
    NO_FRIEND_REQUEST(HttpStatus.NOT_FOUND, "MEMBER004", "친구 요청이 존재하지 않습니다"),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "MEMBER005", "파일 업로드에 실패하였습니다."),
    FILE_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "MEMBER006", "잘못된 형식의 파일입니다."),
    NO_INVITATION_LINK(HttpStatus.NOT_FOUND, "MEMBER007","커뮤니티 초대 링크가 없습니다"),

    // Story Part
    NO_STORY_ERROR(HttpStatus.NOT_FOUND, "STORY001", "스토리 정보가 없습니다."),
    UNAUTHORIZED_STORY_ERROR(HttpStatus.FORBIDDEN, "STORY002", "스토리를 삭제할 권한이 없습니다."),
    MAX_STORY_QUOTA_REACHED_MEMBER(HttpStatus.FORBIDDEN, "STORY003", "사용자의 스토리 작성 할당량이 초과되었습니다."),
    NOT_DEFINED_SORTING_ACTION(HttpStatus.FORBIDDEN, "STORY004", "정의된 정렬방식이 아닙니다."),
    MAX_STORY_QUOTA_REACHED_STORYBOX(HttpStatus.FORBIDDEN, "STORY005", "스토리박스의 스토리 작성 할당량이 초과되었습니다."),
    TIME_FINISHED_ERROR(HttpStatus.BAD_REQUEST, "STORY006", "스토리를 작성할 수 있는 시간이 지났습니다."),

    // Story Box Part
    NO_STORY_BOX_ERROR(HttpStatus.NOT_FOUND, "SBOX001", "스토리 박스 정보가 없습니다."),
    UNAUTHORIZED_STORY_BOX_ERROR(HttpStatus.FORBIDDEN, "SBOX002", "스토리 박스를 삭제할 권한이 없습니다."),
    ALREADY_JOIN_ERROR(HttpStatus.CONFLICT, "SBOX003", "이미 들어가있는 스토리 박스 입니다."),
    UNAUTHORIZED_MEMBER_ERROR(HttpStatus.CONFLICT, "SBOX004", "들어가 있지 않는 스토리 박스 입니다."),
    NO_STORY_BOX_NAME_ERROR(HttpStatus.BAD_REQUEST, "SBOX005", "스토리 박스의 이름을 길이를 맞춰주세요."),
    NO_INVITATION_STORY_BOX(HttpStatus.BAD_REQUEST, "SBOX006", "들어갈 수 있는 기간이 지났습니다."),
    NO_MATCH_STORY_BOX_ERROR(HttpStatus.BAD_REQUEST, "SBOX007", "옳지 않은 스토리 박스 정보 입니다."),
    MAX_MEMBER_QUOTA_REACHED_IN_STORYBOX(HttpStatus.BAD_REQUEST, "SBOX008", "들어갈 수 있는 사용자의 제한을 넘었습니다."),

    // Fire Base Token Part
    FAILED_TO_SEND_MESSAGE(HttpStatus.BAD_REQUEST, "FBSEND001", "Notification 전송에 실패했습니다."),
    FAILED_TO_MAKE_MESSAGE(HttpStatus.BAD_REQUEST, "FBSEND002", "메시지 생성에 실패했습니다."),


    // Report Part
    ALREADY_REPORTED(HttpStatus.CONFLICT, "REPORT001", "이미 신고한 스토리입니다."),
    NO_REPORT_ERROR(HttpStatus.NOT_FOUND, "REPORT002", "신고가 조회되지 않았습니다.");

    
    private HttpStatus status;
    private String code;
    private final String message;


}
