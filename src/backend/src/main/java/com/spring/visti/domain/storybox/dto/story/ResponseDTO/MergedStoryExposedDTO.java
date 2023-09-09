package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.StoryType;
import com.spring.visti.domain.storybox.entity.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MergedStoryExposedDTO {
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

    @Builder
    public MergedStoryExposedDTO(Long id, Long storyBoxId, Member member,
                           StoryType main_file_type, String main_file_path,
                           StoryType sub_file_type, String sub_file_path,
                           Boolean blind, LocalDateTime created_at, LocalDateTime finish_at, Boolean like
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
    }

    public static MergedStoryExposedDTO of(Story story, Boolean like){
        return MergedStoryExposedDTO.builder()
                .id(story.getId())
                .storyBoxId(story.getStoryBox().getId())
                .member(story.getMember())
                .main_file_type(story.getMain_file_type())
                .main_file_path(story.getMain_file_path())
                .sub_file_type(story.getSub_file_type())
                .sub_file_path(story.getSub_file_path())
                .blind(story.getStoryBox().getBlind())
                .created_at(story.getCreate_at())
                .finish_at(story.getStoryBox().getFinish_at())
                .like(like)
                .build();
    }
}