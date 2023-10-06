package com.spring.visti.domain.member.dto.ResponseDTO;


import com.spring.visti.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberExposedDTO {
    private String nickname;
    private String profilePath;
    private Boolean status;

    @Builder
    public MemberExposedDTO(String nickname, String profilePath, Boolean status){
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.status = status;
    }

    public static MemberExposedDTO of(Member member){
        return MemberExposedDTO.builder()
                .nickname(member.getNickname())
                .profilePath(member.getProfilePath())
                .status(member.getStatus())
                .build();
    }
}
