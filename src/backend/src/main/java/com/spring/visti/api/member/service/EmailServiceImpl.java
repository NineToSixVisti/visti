package com.spring.visti.api.member.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.global.redis.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;
import java.util.Random;



@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponseDTO<String> sendMail(String email, String type) throws MessagingException {

        // 인증 코드 생성
        String authNum = createCode();

        String subject = "";
        if(type.equals("find")) {
            // 임시 비밀번호는 생성 후 DB에 저장
            subject = "[팀명] 임시 비밀번호 입니다.";

            // 임시 비밀번호로 Update
            changePassword(email, authNum);
        }else if(type.equals("certification")){
            // 인증 작업은 Redis에 저장한 다음 진행
            subject = "[팀명] 회원가입 인증번호입니다.";

            // 인증 코드 Redis에 저장
            authService.saveAuthCode(email, authNum);
        }else{
            return new BaseResponseDTO<>("이메일 전송에 실패했습니다.", 400);
        }

        // Mail로 전송
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(email); // 메일 수신자
        mimeMessageHelper.setSubject(subject); // 메일 제목
        mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부

        javaMailSender.send(mimeMessage);

        log.info("Send Mail to " + email + " Success");

        return new BaseResponseDTO<>("이메일이 전송되었습니다.", 200);
    }

    private void changePassword(String email, String authNum){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        String encryptedPassword = passwordEncoder.encode(authNum);
        if (optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.updatePassword(encryptedPassword);
            memberRepository.save(member);
        }
    }

    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}