package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

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


    private StoryType file_type;
    private String file_path;

    private Boolean blind;
    private LocalDateTime created_at;
    private LocalDateTime finish_at;
    private Boolean like;

    @Builder
    public StoryExposedDTO(Long id, Long storyBoxId, Member member,
                           StoryType file_type, String file_path,
                           Boolean blind, LocalDateTime created_at, LocalDateTime finish_at, Boolean like
                        ){
        this.id = id;
        this.storyBoxId = storyBoxId;
        this.member = MemberExposedDTO.of(member);
        this.file_type = file_type;
        this.file_path = file_path;

        this.blind = blind;
        this.created_at = created_at;
        this.finish_at = finish_at;
        this.like = like;
    }

    public static StoryExposedDTO of(Story story, Boolean like){
        return StoryExposedDTO.builder()
                .id(story.getId())
                .storyBoxId(story.getStoryBox().getId())
                .member(story.getMember())
                .file_type(story.getMain_file_type())
                .file_path(story.getMain_file_path())
                .blind(story.getStoryBox().getBlind())
                .created_at(story.getCreateAt())
                .finish_at(story.getStoryBox().getFinish_at())
                .like(like)
                .build();
    }
}
