package com.spring.visti.domain.storybox.dto.story.ResponseDTO;

import com.spring.visti.domain.member.entity.Member;
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
    private Member member;
    private String letter_path;
    private String image_path;
    private String audio_path;
    private String video_path;
    private Boolean blind;
    private LocalDateTime finish_at;
    private Boolean like;

    @Builder
    public StoryExposedDTO(Long id, Member member, String letter_path,
                        String image_path, String audio_path, String video_path,
                           Boolean blind, LocalDateTime finish_at, Boolean like
                        ){
        this.id = id;
        this.member = member;
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
        this.blind = blind;
        this.finish_at = finish_at;
        this.like = like;
    }

    public static StoryExposedDTO of(Story story, Boolean like){
        return StoryExposedDTO.builder()
                .id(story.getId())
                .member(story.getMember())
                .letter_path(story.getLetter_path())
                .image_path(story.getImage_path())
                .audio_path(story.getAudio_path())
                .video_path(story.getVideo_path())
                .blind(story.getBlind())
                .finish_at(story.getFinish_at())
                .like(like)
                .build();
    }
}
