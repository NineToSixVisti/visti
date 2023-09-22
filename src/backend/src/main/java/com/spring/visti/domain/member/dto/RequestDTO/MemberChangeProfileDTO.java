package com.spring.visti.domain.member.dto.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberChangeProfileDTO {
    private String newEmail;
    private String nickname;
//    private String profilePath;


    @Builder
    public MemberChangeProfileDTO(String newEmail, String nickname){
        this.newEmail = newEmail;
        this.nickname = nickname;
//        this.profilePath = profilePath;
    }
}



