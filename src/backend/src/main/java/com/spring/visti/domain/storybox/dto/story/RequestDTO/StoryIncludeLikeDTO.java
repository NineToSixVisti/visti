package com.spring.visti.domain.storybox.dto.story.RequestDTO;

import com.spring.visti.domain.storybox.entity.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryIncludeLikeDTO {
    private Story story;
    private boolean isLiked;

    @Builder
    public StoryIncludeLikeDTO(Story story, boolean isLiked) {
        this.story = story;
        this.isLiked = isLiked;
    }

}
