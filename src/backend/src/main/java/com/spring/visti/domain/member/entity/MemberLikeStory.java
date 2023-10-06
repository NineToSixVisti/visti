package com.spring.visti.domain.member.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.storybox.entity.Story;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class MemberLikeStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Story.class)
    @JoinColumn(name = "story_id")
    private Story story;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public MemberLikeStory(Member member, Story story, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.member = member;
        this.story = story;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberLikeStory likeThis(Member member, Story story){
        return MemberLikeStory.builder()
                .member(member)
                .createdAt(story.getCreatedAt())
                .updatedAt(story.getUpdatedAt())
                .story(story)
                .build();
    }
}
