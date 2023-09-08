package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxBuildDTO {

    private String box_img_path;
    private String name;
    private String detail;
    private Boolean blind;
    private LocalDateTime finish_at;

    @Builder
    public StoryBoxBuildDTO(String box_img_path, String name, String detail, Boolean blind, LocalDateTime finish_at){
        this.box_img_path = box_img_path;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finish_at = finish_at;
    }

    public StoryBox toEntity(Member member){

        return  StoryBox.builder()
                .creator(member)
                .box_img_path(box_img_path)
                .name(name)
                .detail(detail)
                .blind(blind)
                .finish_at(finish_at)
                .build();
    }
}
