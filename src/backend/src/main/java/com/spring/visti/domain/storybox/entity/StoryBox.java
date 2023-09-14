package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
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
public class StoryBox extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member creator;

    @Column(columnDefinition = "LONGTEXT")
    private String boxImgPath;

    @Column(length = 32)
    private String name;

    @Column
    private String detail;

    @Column(updatable = false)
    private Boolean blind;

    @Column(updatable = false, name="finished_at")
    private LocalDateTime finishedAt;

    @Column
    private String token;

    @Column
    private LocalDateTime expireTime;

    @OneToMany(mappedBy = "storyBox")
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "storyBox")
    private List<StoryBoxMember> storyBoxMembers = new ArrayList<>();

    @Builder
    public StoryBox(Member creator, String boxImgPath, String name, String detail, Boolean blind, LocalDateTime finishedAt){
        this.creator = creator;
        this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finishedAt = finishedAt;
    }

    public void updateStoryBox(StoryBoxSetDTO storyBoxSetDTO) {
        if(storyBoxSetDTO.getBoxImgPath() != null) {
            this.boxImgPath = storyBoxSetDTO.getBoxImgPath();
        }
        if(storyBoxSetDTO.getName() != null) {
            this.name = storyBoxSetDTO.getName();
        }
        if(storyBoxSetDTO.getDetail() != null) {
            this.detail = storyBoxSetDTO.getDetail();
        }
    }

    public void updateToken(String token, LocalDateTime expireTime){
        this.token = token;
        this.expireTime = expireTime;
    }

}
