package com.spring.visti.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Authentication Part
    REGISTER_PW_LEN_ERROR(HttpStatus.BAD_REQUEST, "AUTH001", "비밀번호는 8자 이상이어야 합니다."),
    EMAIL_INPUT_ERROR(HttpStatus.BAD_REQUEST, "AUTH002", "이메일 인증 번호가 잘못되었습니다."),
    REGISTER_DUPLICATED_EMAIL(HttpStatus.CONFLICT, "AUTH003", "이미 가입되어 있는 유저입니다."),
    LOGIN_INFO_ERROR(HttpStatus.UNAUTHORIZED, "AUTH004", "로그인 정보가 일치하지 않습니다."),
    NO_MEMBER_ERROR(HttpStatus.NOT_FOUND, "AUTH005", "유저 정보가 없습니다."),

    // JWT Notification Part
    NO_TOKEN_HEADER(HttpStatus.UNAUTHORIZED, "AUTH010", "토큰이 존재하지 않습니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "AUTH011", "잘못된 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH012", "만료된 JWT 토큰입니다."),
    JWT_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "AUTH013", "지원되지 않는 JWT 토큰입니다."),
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

    // Story Box Part
    NO_STORY_BOX_ERROR(HttpStatus.NOT_FOUND, "SBOX001", "스토리 박스 정보가 없습니다."),
    UNAUTHORIZED_STORY_BOX_ERROR(HttpStatus.FORBIDDEN, "SBOX002", "스토리 박스를 삭제할 권한이 없습니다."),
    ALREADY_JOIN_ERROR(HttpStatus.CONFLICT, "SBOX003", "이미 들어가있는 스토리 박스 입니다.");
    
    private HttpStatus status;
    private String code;
    private final String message;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
