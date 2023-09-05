package com.spring.demo.domain.storybox.entity;

import com.spring.demo.domain.common.entity.BaseEntity;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.member.entity.MemberStory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    private Member member;

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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime finish_at;

    @OneToMany(mappedBy = "storybox")
    private List<Story> stories = new ArrayList<>();

    @Builder
    public StoryBox(String box_img_path, String storybox_url, String name, String detail, Boolean blind){
        this.box_img_path = box_img_path;
        this.storybox_url = storybox_url;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
    }


}
