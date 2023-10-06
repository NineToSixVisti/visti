package com.spring.visti.domain.member.dto.ResponseDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberStoryBoxExposedDTO {
    private String nickname;
    private String profilePath;
    private Boolean status;
    private Position position;

    @Builder
    public MemberStoryBoxExposedDTO(String nickname, String profilePath, Boolean status, Position position){
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.status = status;
        this.position = position;
    }

    public static MemberStoryBoxExposedDTO toResponse(Member member, Position position){
        return MemberStoryBoxExposedDTO.builder()
                .nickname(member.getNickname())
                .profilePath(member.getProfilePath())
                .status(member.getStatus())
                .position(position)
                .build();
    }
}
