package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxDetailDTO {

    private String box_img_path;
    private String name;
    private MemberExposedDTO creator;
    private String detail;

    private LocalDateTime create_at;
    private LocalDateTime finish_at;

    private Integer member_num;
    private Integer story_num;

    private Boolean blind;

    @Builder
    public StoryBoxDetailDTO(
            String box_img_path, String name, String detail, MemberExposedDTO creator,
            Boolean blind, Integer member_num, Integer story_num,
            LocalDateTime create_at, LocalDateTime finish_at
                            ){
        this.box_img_path = box_img_path;
        this.name = name;
        this.detail = detail;
        this.creator = creator;

        this.blind = blind;
        this.member_num = member_num;
        this.story_num = story_num;

        this.create_at = create_at;
        this.finish_at = finish_at;
    }

    public static StoryBoxDetailDTO toDetailDTO(StoryBox storyBox){
        MemberExposedDTO creatorInfo = MemberExposedDTO.of(storyBox.getCreator());

        Integer member_num = storyBox.getStoryBoxMembers().size();
        Integer story_num = storyBox.getStories().size();

        return StoryBoxDetailDTO.builder()
                .box_img_path(storyBox.getBox_img_path())
                .name(storyBox.getName())
                .detail(storyBox.getDetail())
                .creator(creatorInfo)
                .blind(storyBox.getBlind())
                .member_num(member_num)
                .story_num(story_num)
                .create_at(storyBox.getCreate_at())
                .finish_at(storyBox.getFinish_at())
                .build();
    }
}
