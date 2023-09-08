package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.storybox.constant.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxMemberListDTO {
    private MemberExposedDTO memberExposedDTO;
    private Position position;

    @Builder
    public StoryBoxMemberListDTO(MemberExposedDTO memberExposedDTO, Position position){
        this.memberExposedDTO = memberExposedDTO;
        this.position = position;
    }

    public static StoryBoxMemberListDTO toResponse(MemberExposedDTO memberExposed, Position position){
        return StoryBoxMemberListDTO.builder()
                .memberExposedDTO(memberExposed)
                .position(position)
                .build();
    }
}
