package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.spring.visti.domain.member.entity.Member;
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
    private Member creator;
    private String detail;

    private LocalDateTime create_at;
    private LocalDateTime finish_at;

    private Integer member_num;
    private Integer story_num;

    private Boolean blind;

    @Builder
    public StoryBoxDetailDTO(/*Member creator, String box_img_path, String name, Boolean blind,
                             String detail, Integer member_num, Integer story_num,
                             LocalDateTime create_at, LocalDateTime finish_at*/
            String detail, Integer member_num, Integer story_num
                            ){

        this.detail = detail;
        this.member_num = member_num;
        this.story_num = story_num;
    }

    public static StoryBoxDetailDTO toDetailDTO(StoryBox storyBox){
        Integer member_num = storyBox.getStoryBoxMembers().size();
        Integer story_num = storyBox.getStories().size();

        return StoryBoxDetailDTO.builder()
                .detail(storyBox.getDetail())
                .member_num(member_num)
                .story_num(story_num)
                .build();
    }
}
