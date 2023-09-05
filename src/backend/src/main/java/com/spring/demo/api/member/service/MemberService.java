package com.spring.demo.api.member.service;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.member.dto.MemberInformDTO;
import com.spring.demo.domain.member.dto.MemberJoinDTO;
import com.spring.demo.domain.member.dto.MemberLoginDTO;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.global.jwt.dto.TokenDTO;
import com.spring.demo.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    BaseResponseDTO<String> signUp(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> verifyMember(MemberInformDTO memberInfo);
    BaseResponseDTO<String> verifyEmail(AuthDTO memberInfo);

    BaseResponseDTO<TokenDTO> signIn(MemberLoginDTO memberInfo, HttpServletResponse response);
    BaseResponseDTO<?> signOut(HttpServletRequest request);
    BaseResponseDTO<Member> getInfo(MemberInformDTO memberInfo);
}
