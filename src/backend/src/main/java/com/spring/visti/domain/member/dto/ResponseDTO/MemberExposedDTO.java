package com.spring.visti.domain.member.dto.ResponseDTO;


import com.spring.visti.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberExposedDTO {
    private String nickname;
    private String profile_path;
    private Boolean status;

    @Builder
    public MemberExposedDTO(String nickname, String profile_path, Boolean status){
        this.nickname = nickname;
        this.profile_path = profile_path;
        this.status = status;
    }

    public static MemberExposedDTO of(Member member){
        return MemberExposedDTO.builder()
                .nickname(member.getNickname())
                .profile_path(member.getProfile_path())
                .status(member.getStatus())
                .build();
    }
}
