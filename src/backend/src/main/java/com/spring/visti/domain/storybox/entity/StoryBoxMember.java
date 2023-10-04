package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.Position;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Position position;

    @Column
    private Integer reportCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Builder
    public StoryBoxMember(Member member, StoryBox storyBox, Position position, LocalDateTime createdAt, LocalDateTime finishedAt){
        this.member = member;
        this.storyBox = storyBox;
        this.position = position;
        this.reportCount = 0;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
    }

    public static StoryBoxMember joinBox(Member member, StoryBox storyBox, Position position){
        return StoryBoxMember.builder()
                .member(member)
                .storyBox(storyBox)
                .position(position)
                .finishedAt(storyBox.getFinishedAt())
                .createdAt(storyBox.getCreatedAt())
                .build();
    }

    public boolean countReport(){
        this.reportCount += 1;

        return this.reportCount >= 5;
    }

}
