package com.spring.visti.domain.member.entity;

import com.spring.visti.domain.storybox.entity.Story;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public MemberLikeStory(Member member, Story story){
        this.member = member;
        this.story = story;
    }

    public MemberLikeStory likeThis(Member member, Story story){
        return MemberLikeStory.builder()
                .member(member)
                .story(story)
                .build();
    }
}
