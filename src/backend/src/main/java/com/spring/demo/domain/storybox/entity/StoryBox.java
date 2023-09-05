package com.spring.demo.domain.storybox.entity;

import com.spring.demo.domain.common.entity.BaseEntity;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.storybox.dto.storybox.StoryBoxSetDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private String storybox_url;

    @Column
    private String name;

    @Column
    private String detail;

    @Column
    private Boolean blind;

    @Column(updatable = false)
    private LocalDateTime finish_at;

    @OneToMany(mappedBy = "storybox")
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "storybox")
    private List<StoryBoxMember> storyBoxMembers = new ArrayList<>();

    @Builder
    public StoryBox(Member creator, String box_img_path, String storybox_url, String name, String detail, Boolean blind, LocalDateTime finish_at){
        this.creator = creator;
        this.box_img_path = box_img_path;
        this.storybox_url = storybox_url;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finish_at = finish_at;
    }

    public void updateStoryBox(StoryBoxSetDTO storyBoxSetDTO) {
        if(storyBoxSetDTO.getBox_img_path() != null) {
            this.box_img_path = storyBoxSetDTO.getBox_img_path();
        }
        if(storyBoxSetDTO.getStorybox_url() != null) {
            this.storybox_url = storyBoxSetDTO.getStorybox_url();
        }
        if(storyBoxSetDTO.getName() != null) {
            this.name = storyBoxSetDTO.getName();
        }
        if(storyBoxSetDTO.getDetail() != null) {
            this.detail = storyBoxSetDTO.getDetail();
        }
        if(storyBoxSetDTO.getBlind() != null) {
            this.blind = storyBoxSetDTO.getBlind();
        }
    }

}
