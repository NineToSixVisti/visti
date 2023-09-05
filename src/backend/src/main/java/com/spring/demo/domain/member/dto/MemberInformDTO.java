package com.spring.demo.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInformDTO {
    private String email;

    @Builder
    public MemberInformDTO(String email){
        this.email = email;
    }
//    private final
}
