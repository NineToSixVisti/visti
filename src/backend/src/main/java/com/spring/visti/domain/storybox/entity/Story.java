package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Story extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Member.class)
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
    @Column(updatable = false)
    private Boolean blind;
    @Column(updatable = false)
    private LocalDateTime finish_at;

    @Column
    private Integer reportedCount;



    @OneToMany(mappedBy = "story")
    private List<MemberLikeStory> membersLiked = new ArrayList<>();

    @ManyToOne(targetEntity = StoryBox.class)
    @JoinColumn(name = "storybox_id")
    private StoryBox storyBox;

    @Builder
    public Story(Member member, String letter_path, String image_path, String audio_path, String video_path, StoryBox storyBox, Boolean blind, LocalDateTime finish_at){
        this.member = member;
        this.letter_path = letter_path;
        this.image_path = image_path;
        this.audio_path = audio_path;
        this.video_path = video_path;
        this.storyBox = storyBox;
        this.blind = blind;
        this.finish_at = finish_at;
        this.reportedCount = 0;
    }

    public void makeNFT(String secret_key, String nft_hash){
        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }

    public void updateReportCount(Integer reportedCount){this.reportedCount = reportedCount;}
}
