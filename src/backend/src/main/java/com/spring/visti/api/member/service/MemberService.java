package com.spring.visti.api.member.service;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.member.dto.RequestDTO.MemberInformDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberMyInfoDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberMyInfoProfileDTO;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService extends DefaultService {
    BaseResponseDTO<String> signUp(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> verifyMember(String email, String type);
    BaseResponseDTO<String> verifyAuthNum(AuthDTO memberInfo);

    BaseResponseDTO<TokenDTO> signIn(MemberLoginDTO memberInfo, HttpServletResponse response);
    BaseResponseDTO<?> signOut(HttpServletRequest request);
    BaseResponseDTO<MemberMyInfoDTO> getInfo(String email);

    BaseResponseDTO<MemberMyInfoProfileDTO> getMyData(String email);
}
