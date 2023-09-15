package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryInfoDTO {

    private Long id;
    private Long storyBoxId;
    private MemberExposedDTO member;


    private StoryType mainFileType;
    private String mainFilePath;

    private StoryType subFileType;
    private String subFilePath;


    private Boolean blind;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;
    private Boolean like;

    private String secret_key;
    private String nft_hash;

    @Builder
    public StoryInfoDTO(Long id, Long storyBoxId, Member member,
                        StoryType mainFileType, String mainFilePath,
                        StoryType subFileType, String subFilePath,
                        Boolean blind, LocalDateTime finishedAt, LocalDateTime createdAt, Boolean like,
                        String secret_key, String nft_hash
    ){
        this.id = id;
        this.storyBoxId = storyBoxId;
        this.member = MemberExposedDTO.of(member);

        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;

        this.subFileType = subFileType;
        this.subFilePath = subFilePath;

        this.blind = blind;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.like = like;

        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }
}
