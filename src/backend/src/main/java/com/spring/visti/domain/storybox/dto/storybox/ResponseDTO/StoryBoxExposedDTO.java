package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxExposedDTO {

    private Long id;
    private String box_img_path;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime finished_at;
    private Boolean blind;

    @Builder
    public StoryBoxExposedDTO(Long id, String box_img_path, String name,
                              LocalDateTime created_at, LocalDateTime finished_at,
                              Boolean blind){
        this.id = id;
        this.box_img_path = box_img_path;
        this.name = name;
        this.created_at = created_at;
        this.finished_at = finished_at;
        this.blind = blind;
    }

    public static StoryBoxExposedDTO of(StoryBox storyBox){
        return StoryBoxExposedDTO.builder()
                .id(storyBox.getId())
                .box_img_path(storyBox.getBox_img_path())
                .name(storyBox.getName())
                .created_at(storyBox.getCreate_at())
                .finished_at(storyBox.getFinish_at())
                .blind(storyBox.getBlind())
                .build();
    }
}
