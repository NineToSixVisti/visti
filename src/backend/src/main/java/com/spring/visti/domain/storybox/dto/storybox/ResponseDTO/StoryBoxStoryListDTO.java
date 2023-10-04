package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxStoryListDTO {
    private StoryExposedDTO storyExposedDTO;
    private Boolean like;

    @Builder
    public StoryBoxStoryListDTO(StoryExposedDTO storyExposedDTO, Boolean like){
        this.storyExposedDTO = storyExposedDTO;
        this.like = like;
    }

    public static StoryBoxStoryListDTO toResponse(StoryExposedDTO storyExposedDTO, Boolean like){
        return StoryBoxStoryListDTO.builder()
                .storyExposedDTO(storyExposedDTO)
                .like(like)
                .build();
    }

}
