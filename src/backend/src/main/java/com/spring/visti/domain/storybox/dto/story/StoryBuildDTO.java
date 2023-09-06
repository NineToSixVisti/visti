package com.spring.visti.domain.storybox.dto.story;


import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryBuildDTO {

    private String letter_path;
    private String image_path;
    private String audio_path;
    private String video_path;

    @Builder
    public StoryBuildDTO(String letter_path, String image_path, String audio_path, String video_path){
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
    }

    public Story toEntity(Member member){

        return  Story.builder()
                .member(member)
                .letter_path(letter_path)
                .image_path(image_path)
                .audio_path(audio_path)
                .video_path(video_path)
                .build();
    }
}

