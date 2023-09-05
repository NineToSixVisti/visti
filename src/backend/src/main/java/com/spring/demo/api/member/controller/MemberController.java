package com.spring.demo.api.member.controller;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.api.member.service.EmailService;
import com.spring.demo.api.member.service.MemberService;
import com.spring.demo.domain.member.dto.MemberInformDTO;
import com.spring.demo.domain.member.dto.MemberJoinDTO;
import com.spring.demo.domain.member.dto.MemberLoginDTO;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.global.jwt.dto.TokenDTO;
import com.spring.demo.global.redis.dto.AuthDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member 컨트롤러", description = "Member Controller API Document")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> signUp(@RequestBody MemberJoinDTO memberInfo) {
        BaseResponseDTO<String> response = memberService.signUp(memberInfo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/verifyMember")
    @Operation(summary = "이메일 중복확인", description = "이메일이 중복이 되는지 확인합니다.", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> verifyMember(@RequestBody MemberInformDTO memberInfo){
        BaseResponseDTO<String> response = memberService.verifyMember(memberInfo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/verifyAuthNum")
    @Operation(summary = "이메일 인증[이메일 전송]", description = "이메일이 중복이 되는지 확인을 위한 메일 전송을 진행합니다..", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> sendMail(@RequestParam String email, @RequestParam String type)
            throws MessagingException {
        BaseResponseDTO<String> response = emailService.sendMail(email, type);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/verifyAuthNum")
    @Operation(summary = "이메일 인증[이메일 확인]", description = "이메일이 중복이 되는지 확인합니다.", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> verifyEmail(@RequestBody AuthDTO verificationCode){
        BaseResponseDTO<String> response = memberService.verifyEmail(verificationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "Id, PW로 로그인 진행", tags = {"메인 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<TokenDTO>> signIn(@RequestBody MemberLoginDTO memberInfo, HttpServletResponse httpResponse){
        BaseResponseDTO<TokenDTO> response = memberService.signIn(memberInfo, httpResponse);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/signout")
    @Operation(summary = "로그 아웃", description = "버튼 클릭시 로그아웃 진행[구현 안됨]", tags = {"설정"})
    public ResponseEntity<?> signOut(HttpServletResponse httpResponse){
        return null;
    }


    @PostMapping("/inform")
    @Operation(summary = "사용자 정보", description = "Post를 통해 받은 Email을 통해 정보를 받아옴", tags = {"설정"})
    public  ResponseEntity<? extends BaseResponseDTO<Member>> getInfo(@RequestBody MemberInformDTO memberInfo){
        BaseResponseDTO<Member> response = memberService.getInfo(memberInfo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
