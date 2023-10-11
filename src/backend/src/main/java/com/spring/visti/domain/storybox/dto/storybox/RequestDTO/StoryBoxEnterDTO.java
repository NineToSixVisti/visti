package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxEnterDTO {
    private String encryptedId;

    @Builder
    public StoryBoxEnterDTO(String encryptedId){
        this.encryptedId = encryptedId;
    }

}
