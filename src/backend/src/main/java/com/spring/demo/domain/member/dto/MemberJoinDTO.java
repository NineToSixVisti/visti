package com.spring.demo.domain.member.dto;

import com.spring.demo.domain.member.constant.MemberType;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Setter

@Getter
@NoArgsConstructor
public class MemberJoinDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;

    @Builder
    public MemberJoinDTO(String email, String password, String name, String nickname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public Member toEntity(String password, MemberType memberType){
//        System.out.println("이거 들어가나요");
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .role(Role.from("USER"))
                .memberType(memberType)
                .build();
    }
}

//@Getter
//public record MemberJoinDTO(String email, String password, String name, String nickname) {
//    @Builder
//    public MemberJoinDTO {
//    }
//
//    public Member toEntity(String password) {
////        System.out.println("이거 들어가나요");
//        return Member.builder()
//                .email(email)
//                .password(password)
//                .name(name)
//                .nickname(nickname)
//                .role(Role.from("USER"))
//                .build();
//    }
//}
