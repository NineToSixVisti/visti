package com.spring.demo.api.member.service;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.member.constant.MemberType;
import com.spring.demo.domain.member.dto.MemberInformDTO;
import com.spring.demo.domain.member.dto.MemberJoinDTO;

import com.spring.demo.domain.member.dto.MemberLoginDTO;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.utils.exception.ApiException;
import com.spring.demo.domain.member.repository.MemberRepository;
import com.spring.demo.global.jwt.dto.TokenDTO;
import com.spring.demo.global.jwt.service.TokenProvider;
import com.spring.demo.global.redis.dto.AuthDTO;
import com.spring.demo.global.redis.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static com.spring.demo.utils.exception.ErrorCode.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final EmailService emailService;
    private final AuthService authService;

    @Override
    public BaseResponseDTO<String> signUp(MemberJoinDTO memberInfo)
            throws RuntimeException {
        /*
        example
         memberInfo = {
            email : example@example.com
            name : myName
            nickname : myNickname
            password : password
         }
        */
        if(memberRepository.findByEmail(memberInfo.getEmail()).isPresent()){
            throw new ApiException(REGISTER_DUPLICATED_EMAIL);
        }

        String encryptedPassword = passwordEncoder.encode(memberInfo.getPassword());

        Member member = memberInfo.toEntity(encryptedPassword, MemberType.SOCIAL);

        memberRepository.save(member);

        return new BaseResponseDTO<>("회원가입이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> verifyMember(MemberInformDTO memberInfo) {
        /*
        example
         memberInfo = {
            email : example@example.com
         }
        */
        if(memberRepository.findByEmail(memberInfo.getEmail()).isPresent()){
            throw new ApiException(REGISTER_DUPLICATED_EMAIL);
        }
        return new BaseResponseDTO<>("사용 가능한 Email 입니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> verifyEmail(AuthDTO authInfo) {
        /*
        example
         authNumber = {
            email : example@email.com;
            authNum : authNum;
            type : "certification";
         }
        */
        String email = authInfo.getEmail();
        // 인증 코드 조회
        String authCode = authService.getAuthCode(email);
        if (!Objects.equals(authCode, authInfo.getAuthNum())){

            throw new ApiException(EMAIL_INPUT_ERROR);
        }

        log.info("이메일 인증 완료.");
        return new BaseResponseDTO<>("이메일 인증이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<TokenDTO> signIn(MemberLoginDTO memberInfo, HttpServletResponse httpResponse) throws RuntimeException { // 이후 내껄로 수정해야함
        /*
        example
         memberInfo = {
            email : example@example.com
            password : password
         }
        */

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberInfo.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

            String accessToken = tokenDTO.getAccessToken();
            String refreshToken = tokenDTO.getRefreshToken();

            // 4. User 객체에서 username 을 가져옵니다.
            String email = authentication.getName();

            // 5. email 을 사용하여 DB 에서 Member 객체를 검색합니다.
            Optional<Member> optionalMember = memberRepository.findByEmail(email);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();

                // refreshToken 을 업데이트하고 데이터베이스에 저장합니다.
                member.updateMemberToken(refreshToken);
                memberRepository.save(member);
            } else {
                // DB에 해당 이메일을 가진 Member 가 없는 경우의 처리
                throw new ApiException(NO_MEMBER_ERROR);

            }

            // 6. 토큰 정보를 Header 로 등록
            tokenProvider.setHeaderAccessToken(httpResponse, accessToken);
            tokenProvider.setHeaderRefreshToken(httpResponse, refreshToken);

            return new BaseResponseDTO<TokenDTO>("로그인이 완료되었습니다.", 200, tokenDTO);
        }catch (Exception e){
            throw new ApiException(LOGIN_INFO_ERROR);
        }
    }

    @Override
    public BaseResponseDTO<?> signOut(HttpServletRequest httpRequest) {
        // 토큰 탐색
        String accessToken = tokenProvider.getHeaderToken(httpRequest, "Access");
        String refreshToken = tokenProvider.getHeaderToken(httpRequest, "Refresh");

        String email = (String) tokenProvider.parseClaims(accessToken).get("user_email");
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.updateMemberToken(null);
            memberRepository.save(member);
            return new BaseResponseDTO<>("로그아웃이 완료되었습니다.", 200);
        }

        throw new ApiException(NO_MEMBER_ERROR);
    }



    @Override
    public BaseResponseDTO<Member> getInfo(MemberInformDTO memberInfo) {
        /*
        example
         memberInfo = {
            email : example@example.com
         }
        */
        Optional<Member> optionalMember = memberRepository.findByEmail(memberInfo.getEmail());
        System.out.println("Member info: " + optionalMember);

        if (optionalMember.isEmpty()){ throw new ApiException(NO_MEMBER_ERROR); }
//        System.out.println("Member info: " + member.get());

        return new BaseResponseDTO<>(memberInfo.getEmail() + "의 정보입니다.", 200, optionalMember.get());
//        return new BaseResponseDTO("$s 님의 정보를 제공해 드립니다", 200, member);
    }

}
