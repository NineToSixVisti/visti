package com.spring.demo.domain.story.entity;

import com.spring.demo.domain.common.entity.BaseEntity;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.member.entity.MemberStory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Story extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String letter_path;
    @Column
    private String image_path;
    @Column
    private String audio_path;
    @Column
    private String video_path;
    @Column
    private String secret_key;
    @Column
    private String nft_hash;

    @OneToMany(mappedBy = "story")
    private List<MemberStory> membersLiked = new ArrayList<>();

    @Builder
    public Story(String letter_path, String image_path, String audio_path, String video_path){
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
    }

    public void makeNFT(String secret_key, String nft_hash){
        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }
}
