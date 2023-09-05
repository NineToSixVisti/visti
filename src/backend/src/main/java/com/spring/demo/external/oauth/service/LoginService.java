package com.spring.demo.external.oauth.service;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.member.repository.MemberRepository;
import com.spring.demo.global.jwt.dto.TokenDTO;
import com.spring.demo.global.jwt.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public BaseResponseDTO<TokenDTO> SocialLogin(Member member){

        // Authentication 객체를 생성. 주의: 카카오 로그인이므로 password는 null이나 dummy 값을 사용.
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getEmail()); // OAuth 비밀번호는 memberType + ID 로 조합한 email값과 동일

        // Authentication 객체를 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // JWT 토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // refreshToken을 DB에 저장
        member.updateMemberToken(tokenDTO.getRefreshToken());
        memberRepository.save(member);

        // 이 부분은 필요에 따라 클라이언트에 토큰을 반환하는 방식으로 변경할 수 있습니다.
        return new BaseResponseDTO<TokenDTO>(member.getMemberType()+" 로그인이 완료되었습니다.", 200, tokenDTO);
    }
}
