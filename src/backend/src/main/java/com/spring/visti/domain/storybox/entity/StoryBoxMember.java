package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StoryBoxMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = StoryBox.class)
    @JoinColumn(name = "storybox_id")
    private StoryBox storyBox;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public StoryBoxMember(Member member, StoryBox storyBox){
        this.member = member;
        this.storyBox = storyBox;
    }

    public  StoryBoxMember joinBox(Member member, StoryBox storyBox){
        return StoryBoxMember.builder()
                .member(member)
                .storyBox(storyBox)
                .build();
    }

}
