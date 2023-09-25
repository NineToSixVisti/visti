package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxSetDTO {

   // private String boxImgPath;
    private String name;
    private String detail;
    private Boolean blind;
    private LocalDateTime finishedAt;

    @Builder
    public StoryBoxSetDTO(String name, String detail, Boolean blind, LocalDateTime finishedAt){
     //   this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finishedAt = finishedAt;
    }
}
