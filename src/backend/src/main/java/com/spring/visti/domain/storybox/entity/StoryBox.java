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

    @Column
    private String box_img_path;


    @Column
    private String name;

    @Column
    private String detail;

    @Column(updatable = false)
    private Boolean blind;

    @Column(updatable = false)
    private LocalDateTime finish_at;

    @OneToMany(mappedBy = "storybox")
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "storybox")
    private List<StoryBoxMember> storyBoxMembers = new ArrayList<>();

    @Builder
    public StoryBox(Member creator, String box_img_path, String name, String detail, Boolean blind, LocalDateTime finish_at){
        this.creator = creator;
        this.box_img_path = box_img_path;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finish_at = finish_at;
    }

    public void updateStoryBox(StoryBoxSetDTO storyBoxSetDTO) {
        if(storyBoxSetDTO.getBox_img_path() != null) {
            this.box_img_path = storyBoxSetDTO.getBox_img_path();
        }
        if(storyBoxSetDTO.getName() != null) {
            this.name = storyBoxSetDTO.getName();
        }
        if(storyBoxSetDTO.getDetail() != null) {
            this.detail = storyBoxSetDTO.getDetail();
        }
    }

}
