package com.spring.demo.domain.storybox.dto.storybox;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxSetDTO {

    private String box_img_path;
    private String storybox_url;
    private String name;
    private String detail;
    private Boolean blind;

    @Builder
    public StoryBoxSetDTO(String box_img_path, String storybox_url, String name, String detail, Boolean blind){
        this.box_img_path = box_img_path;
        this.storybox_url = storybox_url;
        this.name = name;
        this.detail = detail;
        this. blind = blind;
    }
}
