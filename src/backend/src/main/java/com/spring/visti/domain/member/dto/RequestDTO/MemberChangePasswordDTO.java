package com.spring.visti.domain.member.dto.RequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberChangePasswordDTO {
    private String password;
    @Builder
    public MemberChangePasswordDTO(String email, String password){
        this.password = password;
    }


}
