package com.spring.visti.api.member.service;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.member.dto.RequestDTO.*;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberMyInfoDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberMyInfoProfileDTO;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface MemberService extends DefaultService {
    BaseResponseDTO<String> signUp(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> verifyMember(String email, String type);
    BaseResponseDTO<String> verifyAuthNum(AuthDTO memberInfo);

    BaseResponseDTO<TokenDTO> signIn(MemberLoginDTO memberInfo, HttpServletResponse response);
    BaseResponseDTO<String> signOut(String email, String access_token);

    BaseResponseDTO<String> withdrawalUser(String email, String access_token);

    BaseResponseDTO<MemberMyInfoDTO> getInfo(String email);

    BaseResponseDTO<String> changePassword(String email, String newPW);

    BaseResponseDTO<MemberMyInfoProfileDTO> getMyData(String email);

    BaseResponseDTO<String> changeProfile(String email, MemberChangeProfileDTO memberChangeProfileDTO, MultipartFile file) throws IOException;
}
