package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxExposedDTO {

    private Long id;
    private String boxImgPath;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;
    private Boolean blind;

    @Builder
    public StoryBoxExposedDTO(Long id, String boxImgPath, String name,
                              LocalDateTime createdAt, LocalDateTime finishedAt,
                              Boolean blind){
        this.id = id;
        this.boxImgPath = boxImgPath;
        this.name = name;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.blind = blind;
    }

    public static StoryBoxExposedDTO of(StoryBox storyBox){
        return StoryBoxExposedDTO.builder()
                .id(storyBox.getId())
                .boxImgPath(storyBox.getBoxImgPath())
                .name(storyBox.getName())
                .createdAt(storyBox.getCreatedAt())
                .finishedAt(storyBox.getFinishedAt())
                .blind(storyBox.getBlind())
                .build();
    }
}
