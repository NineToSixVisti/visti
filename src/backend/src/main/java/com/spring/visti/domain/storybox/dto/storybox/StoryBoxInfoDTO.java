package com.spring.visti.domain.storybox.dto.storybox;

import com.spring.visti.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBoxInfoDTO {

    private Long id;
    private Member creator;
    private String box_img_path;
    private String storybox_url;
    private String name;
    private String detail;
    private Boolean blind;

    @Builder
    public StoryBoxInfoDTO(Member creator, String box_img_path, String name, String detail, Boolean blind){
        this.creator = creator;
        this.box_img_path = box_img_path;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
    }
}
