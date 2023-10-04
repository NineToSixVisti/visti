package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxSetDTO {

   // private String boxImgPath;
    private String name;
    private String detail;

    @Builder
    public StoryBoxSetDTO(String name, String detail){
     //   this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
    }
}
