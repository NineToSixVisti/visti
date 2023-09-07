package com.spring.visti.domain.storybox.dto.story.RequestDTO;


import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBuildDTO {

    private String letter_path;
    private String image_path;
    private String audio_path;
    private String video_path;
    private LocalDateTime finish_at;
    private Boolean blind;

    @Builder
    public StoryBuildDTO(String letter_path, String image_path, String audio_path, String video_path, LocalDateTime finish_at, Boolean blind){
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
        this.finish_at = finish_at;
        this.blind = blind;
    }

    public Story toEntity(Member member){

        return  Story.builder()
                .member(member)
                .letter_path(letter_path)
                .image_path(image_path)
                .audio_path(audio_path)
                .video_path(video_path)
                .blind(blind)
                .finish_at(finish_at)
                .build();
    }
}

