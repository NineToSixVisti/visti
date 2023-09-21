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
public class StoryBoxDetailDTO {

//    private String boxImgPath;
    private String name;
//    private MemberExposedDTO creator;
    private String detail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;

    private Integer memberNum;
    private Integer storyNum;

    private Boolean blind;

    @Builder
    public StoryBoxDetailDTO(
            String boxImgPath, String name, String detail, MemberExposedDTO creator,
            Boolean blind, Integer memberNum, Integer storyNum,
            LocalDateTime createdAt, LocalDateTime finishedAt
                            ){
//        this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
//        this.creator = creator;

        this.blind = blind;
        this.memberNum = memberNum;
        this.storyNum = storyNum;

        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
    }

    public static StoryBoxDetailDTO toDetailDTO(StoryBox storyBox){
//        MemberExposedDTO creatorInfo = MemberExposedDTO.of(storyBox.getCreator());

        Integer member_num = storyBox.getStoryBoxMembers().size();
        Integer story_num = storyBox.getStories().size();

        return StoryBoxDetailDTO.builder()
//                .boxImgPath(storyBox.getBoxImgPath())
                .name(storyBox.getName())
                .detail(storyBox.getDetail())
//                .creator(creatorInfo)
                .blind(storyBox.getBlind())
                .memberNum(member_num)
                .storyNum(story_num)
                .createdAt(storyBox.getCreatedAt())
                .finishedAt(storyBox.getFinishedAt())
                .build();
    }
}
