package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

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


    private StoryType main_file_type;
    private String main_file_path;

    private StoryType sub_file_type;
    private String sub_file_path;


    private Boolean blind;
    private LocalDateTime created_at;
    private LocalDateTime finish_at;
    private Boolean like;

    private String secret_key;
    private String nft_hash;

    @Builder
    public StoryInfoDTO(Long id, Long storyBoxId, Member member,
                        StoryType main_file_type, String main_file_path,
                        StoryType sub_file_type, String sub_file_path,
                        Boolean blind, LocalDateTime finish_at, LocalDateTime created_at, Boolean like,
                        String secret_key, String nft_hash
    ){
        this.id = id;
        this.storyBoxId = storyBoxId;
        this.member = MemberExposedDTO.of(member);

        this.main_file_type = main_file_type;
        this.main_file_path = main_file_path;

        this.sub_file_type = sub_file_type;
        this.sub_file_path = sub_file_path;

        this.blind = blind;
        this.created_at = created_at;
        this.finish_at = finish_at;
        this.like = like;

        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }
}
