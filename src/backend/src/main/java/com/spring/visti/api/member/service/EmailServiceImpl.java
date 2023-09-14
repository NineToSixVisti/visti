package com.spring.visti.api.member.service;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.global.redis.service.AuthService;
import com.spring.visti.utils.exception.ApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import static com.spring.visti.utils.exception.ErrorCode.INVALID_EMAIL_FORMAT;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;
    @Transactional
    public BaseResponseDTO<String> sendMail(String email, String type) throws MessagingException {

        // 인증 코드 생성
        String authNum = createCode();
        log.info("====" + email + "에게 이메일 전송을 시도합니다. ======");
        String subject = "";
        if(type.equals("find")) {
            // 임시 비밀번호는 생성 후 DB에 저장
            subject = "[Visti] 임시 비밀번호 입니다.";

            // 임시 비밀번호로 Update
            boolean isEmailChanged = changePassword(email, authNum);
            if (!isEmailChanged){
                log.info("Send Mail to " + email + " Failed");
                return new BaseResponseDTO<>("Email이 데이터베이스에 없습니다.", 200);
            }
        }else if(type.equals("certification")){

            if (!isValidEmail(email)) {
                log.info("Send Mail to " + email + " Failed");
                throw new ApiException(INVALID_EMAIL_FORMAT);
            }

            // 인증 작업은 Redis에 저장한 다음 진행
            subject = "[Visti] 회원가입 인증번호입니다.";

            // 인증 코드 Redis에 저장
            authService.saveAuthCode(email, authNum);
        }else{
            log.info("Send Mail to " + email + " Failed");
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

    private boolean changePassword(String email, String authNum){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        String encryptedPassword = passwordEncoder.encode(authNum);
        if (optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.updatePassword(encryptedPassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    private String createCode() {
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

    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

}
