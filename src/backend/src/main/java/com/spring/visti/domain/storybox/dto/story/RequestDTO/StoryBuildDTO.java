package com.spring.visti.domain.storybox.dto.story.RequestDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class StoryBuildDTO {

    private String storyBoxId;
    private StoryType mainFileType;
    private String mainFilePath;


    @Builder
    public StoryBuildDTO(String storyBoxId,
                         StoryType mainFileType, String mainFilePath
                         ){
        this.storyBoxId = storyBoxId;
        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;
    }

    public Story toEntity(Member member, StoryBox storyBox, String mainFilePath){

        return  Story.builder()
                .member(member)
                .storyBox(storyBox)
                .mainFileType(mainFileType)
                .mainFilePath(mainFilePath)
                .build();
    }
}

