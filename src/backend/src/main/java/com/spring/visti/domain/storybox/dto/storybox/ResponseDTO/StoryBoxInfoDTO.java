package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxInfoDTO {

    private String boxImgPath;
    private String name;
    private Boolean blind;
    private Boolean isHost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;

    @Builder
    public StoryBoxInfoDTO(String boxImgPath, boolean isHost,
                           String name, Boolean blind, LocalDateTime createdAt, LocalDateTime finishedAt){
        this.boxImgPath = boxImgPath;
        this.isHost = isHost;
        this.name = name;
        this.blind = blind;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
    }

    public static StoryBoxInfoDTO toResponse(StoryBox storyBox, boolean isHost){
        return StoryBoxInfoDTO.builder()
                .boxImgPath(storyBox.getBoxImgPath())
                .isHost(isHost)
                .name(storyBox.getName())
                .blind(storyBox.getBlind())
                .createdAt(storyBox.getCreatedAt())
                .finishedAt(storyBox.getFinishedAt())
                .build();
    }
}
