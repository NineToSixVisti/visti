package com.spring.demo.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
public class MemberLoginDTO {

    private String email;
    private String password;
    @Builder
    public MemberLoginDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

//@Getter
//public record MemberLoginDTO(String email, String password) {
//    @Builder
//    public MemberLoginDTO{
//    }
//}

