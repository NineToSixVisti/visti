package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxInfoDTO {

    private String box_img_path;
    private String name;
    private Boolean blind;
    private LocalDateTime create_at;
    private LocalDateTime finish_at;

    @Builder
    public StoryBoxInfoDTO(String box_img_path, String name, Boolean blind, LocalDateTime create_at, LocalDateTime finish_at){
        this.box_img_path = box_img_path;
        this.name = name;
        this.blind = blind;
        this.create_at = create_at;
        this.finish_at = finish_at;
    }

    public static StoryBoxInfoDTO toResponse(StoryBox storyBox){
        return StoryBoxInfoDTO.builder()
                .box_img_path(storyBox.getBox_img_path())
                .name(storyBox.getName())
                .blind(storyBox.getBlind())
                .create_at(storyBox.getCreate_at())
                .finish_at(storyBox.getFinish_at())
                .build();
    }
}
