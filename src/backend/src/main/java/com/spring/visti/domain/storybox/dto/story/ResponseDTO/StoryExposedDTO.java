package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.entity.Story;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryExposedDTO {
    private Long id;
    private Long storyBoxId;
    private MemberExposedDTO member;


    private StoryType mainFileType;
    private String mainFilePath;

//    private StoryType subFileType;
//    private String subFilePath;

    private Boolean blind;
    private Boolean like;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;

    @Builder
    public StoryExposedDTO(Long id, Long storyBoxId, Member member,
                           StoryType mainFileType, String mainFilePath,
                           Boolean blind, LocalDateTime createdAt, LocalDateTime finishedAt, Boolean like
                        ){
        this.id = id;
        this.storyBoxId = storyBoxId;
        this.member = MemberExposedDTO.of(member);
        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;

        this.blind = blind;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.like = like;
    }

    public static StoryExposedDTO of(Story story, Boolean like){
        return StoryExposedDTO.builder()
                .id(story.getId())
                .storyBoxId(story.getStoryBox().getId())
                .member(story.getMember())
                .mainFileType(story.getMainFileType())
                .mainFilePath(story.getMainFilePath())
                .blind(story.getStoryBox().getBlind())
                .createdAt(story.getCreatedAt())
                .finishedAt(story.getStoryBox().getFinishedAt())
                .like(like)
                .build();
    }
}
