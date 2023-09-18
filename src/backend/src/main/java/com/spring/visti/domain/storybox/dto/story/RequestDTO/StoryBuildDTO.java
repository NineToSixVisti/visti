package com.spring.visti.domain.storybox.dto.story.RequestDTO;


import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBuildDTO {

    private Long storyBoxId;
    private StoryType mainFileType;
    private String mainFilePath;

    private StoryType subFileType;
    private String subFilePath;

    @Builder
    public StoryBuildDTO(Long storyBoxId,
                         StoryType mainFileType, String mainFilePath,
                         StoryType subFileType, String subFilePath){
        this.storyBoxId = storyBoxId;
        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;
        this.subFileType = subFileType;
        this.subFilePath = subFilePath;
    }

    public Story toEntity(Member member, StoryBox storyBox, String mainFilePath){

        return  Story.builder()
                .member(member)
                .storyBox(storyBox)
                .mainFileType(mainFileType)
                .mainFilePath(mainFilePath)
                .subFileType(subFileType)
                .subFilePath(subFilePath)
                .build();
    }
}

