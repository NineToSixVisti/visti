package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxSetDTO {

    private String box_img_path;
    private String name;
    private String detail;

    @Builder
    public StoryBoxSetDTO(String box_img_path, String name, String detail){
        this.box_img_path = box_img_path;
        this.name = name;
        this.detail = detail;
    }
}
