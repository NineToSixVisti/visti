package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.common.entity.BaseTimeEntity;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.Position;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StoryBoxMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = StoryBox.class)
    @JoinColumn(name = "storybox_id")
    private StoryBox storyBox;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Position position;

    @Column
    private Integer reportCount;

    @Builder
    public StoryBoxMember(Member member, StoryBox storyBox, Position position){
        this.member = member;
        this.storyBox = storyBox;
        this.position = position;
        this.reportCount = 0;
    }

    public static StoryBoxMember joinBox(Member member, StoryBox storyBox, Position position){
        return StoryBoxMember.builder()
                .member(member)
                .storyBox(storyBox)
                .position(position)
                .build();
    }

    public boolean countReport(){
        this.reportCount += 1;

        return this.reportCount >= 5;
    }

}
