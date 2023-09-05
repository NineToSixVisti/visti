package com.spring.demo.domain.storybox.dto.story;

import com.spring.demo.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryInfoDTO {

    private Member member;
    private String letter_path;
    private String image_path;
    private String audio_path;
    private String video_path;
    private String secret_key;
    private String nft_hash;

    @Builder
    public StoryInfoDTO(Member member, String letter_path,
                        String image_path, String audio_path, String video_path,
                        String secret_key, String nft_hash){

        this.member = member;
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
//        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }
}
