package com.spring.visti.api.member.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.MemberInformDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.member.dto.MemberLoginDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    BaseResponseDTO<String> signUp(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> verifyMember(MemberInformDTO memberInfo);
    BaseResponseDTO<String> verifyEmail(AuthDTO memberInfo);

    BaseResponseDTO<TokenDTO> signIn(MemberLoginDTO memberInfo, HttpServletResponse response);
    BaseResponseDTO<?> signOut(HttpServletRequest request);
    BaseResponseDTO<Member> getInfo(HttpServletRequest httpServletRequest);
}
