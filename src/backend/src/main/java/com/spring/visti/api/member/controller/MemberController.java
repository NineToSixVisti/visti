package com.spring.visti.api.member.controller;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.member.service.EmailService;
import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.domain.member.dto.RequestDTO.MemberInformDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberMyInfoDTO;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<? extends BaseResponseDTO<String>> signUp(
            @RequestBody MemberJoinDTO memberInfo
    ) {
        BaseResponseDTO<String> response = memberService.signUp(memberInfo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/verify-member")
    @Operation(summary = "이메일 중복확인", description = "이메일이 중복이 되는지 확인합니다.", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> verifyMember(
            @RequestBody MemberInformDTO memberInfo
    ){
        BaseResponseDTO<String> response = memberService.verifyMember(memberInfo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/sendmail")
    @Operation(summary = "이메일[이메일 전송]", description = " 메일 전송을 진행합니다.. type : 1. certification [회원 가입] \n 2. find [임시 비밀번호 발급]", tags = {"이메일 전송"})
    public ResponseEntity<? extends BaseResponseDTO<String>> sendMail(
            @RequestParam String email,
            @RequestParam String type
    )
            throws MessagingException {
        BaseResponseDTO<String> response = emailService.sendMail(email, type);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/verify-authnum")
    @Operation(summary = "이메일 인증[이메일 확인]", description = "인증코드 조회를 진행합니다.", tags = {"회원 가입"})
    public ResponseEntity<? extends BaseResponseDTO<String>> verifyAuthNum(
            @RequestBody AuthDTO verificationCode
    ){
        BaseResponseDTO<String> response = memberService.verifyAuthNum(verificationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "Id, PW로 로그인 진행", tags = {"메인 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<TokenDTO>> signIn(
            @RequestBody MemberLoginDTO memberInfo,
            HttpServletResponse httpResponse
    ){
        BaseResponseDTO<TokenDTO> response = memberService.signIn(memberInfo, httpResponse);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/signout")
    @Operation(summary = "로그 아웃", description = "버튼 클릭시 로그아웃 진행[구현 안됨]", tags = {"마이페이지"})
    public ResponseEntity<?> signOut(HttpServletRequest httpServletRequest){
        return ResponseEntity.status(200).body("구현이 안되어있습니다.");
    }


    @GetMapping("/inform")
    @Operation(summary = "사용자 정보", description = "Post를 통해 받은 Email을 통해 정보를 받아옴", tags = {"마이페이지"})
    public  ResponseEntity<? extends BaseResponseDTO<MemberMyInfoDTO>> getInfo(
            HttpServletRequest httpServletRequest
    ){
        BaseResponseDTO<MemberMyInfoDTO> response = memberService.getInfo(httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
