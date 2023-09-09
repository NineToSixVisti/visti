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

    private StoryType main_file_type;
    private String main_file_path;

    private StoryType sub_file_type;
    private String sub_file_path;

    @Builder
    public StoryBuildDTO(Long storyBoxId,
                         StoryType main_file_type, String main_file_path,
                         StoryType sub_file_type, String sub_file_path){
        this.storyBoxId = storyBoxId;
        this.main_file_type = main_file_type;
        this.main_file_path = main_file_path;
        this.sub_file_type = sub_file_type;
        this.sub_file_path = sub_file_path;
    }

    public Story toEntity(Member member, StoryBox storyBox){

        return  Story.builder()
                .member(member)
                .storyBox(storyBox)
                .main_file_type(main_file_type)
                .main_file_path(main_file_path)
                .sub_file_type(sub_file_type)
                .sub_file_path(sub_file_path)
                .build();
    }
}

