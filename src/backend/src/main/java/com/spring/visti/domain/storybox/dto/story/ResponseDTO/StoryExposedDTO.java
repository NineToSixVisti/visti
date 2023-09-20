package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.utils.urlutils.SecurePathUtil;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryExposedDTO {
    private Long id;
    private String encryptedId;

    private Long storyBoxId;
    private String encryptedStoryBoxId;

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
    public StoryExposedDTO(Long id, String encryptedId,
                           Long storyBoxId,  String encryptedStoryBoxId,
                           Member member,
                           StoryType mainFileType, String mainFilePath,
                           Boolean blind, LocalDateTime createdAt, LocalDateTime finishedAt, Boolean like
                        ){
        this.id = id;
        this.encryptedId = encryptedId;

        this.storyBoxId = storyBoxId;
        this.encryptedStoryBoxId = encryptedStoryBoxId;
        this.member = MemberExposedDTO.of(member);
        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;

        this.blind = blind;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.like = like;
    }

    public static StoryExposedDTO of(Story story, Boolean like){
        String encryptedId = SecurePathUtil.encryptAndEncode(String.valueOf(story.getId()));
        String isDecrypted = SecurePathUtil.decodeAndDecrypt(encryptedId);
        System.out.println("이거를 확인해주세요" + isDecrypted);


        String encryptedStoryBoxId = SecurePathUtil.encryptAndEncode(String.valueOf(story.getStoryBox().getId()));
        String isDecryptedStoryBox = SecurePathUtil.decodeAndDecrypt(encryptedStoryBoxId);
        System.out.println("이거를 확인해주세요" + isDecryptedStoryBox);

        return StoryExposedDTO.builder()
                .id(story.getId())
                .encryptedId(encryptedId)
                .storyBoxId(story.getStoryBox().getId())
                .encryptedStoryBoxId(encryptedStoryBoxId)
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
