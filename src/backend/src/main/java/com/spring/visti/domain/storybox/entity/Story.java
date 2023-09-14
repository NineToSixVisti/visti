package com.spring.visti.domain.storybox.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.constant.StoryType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoryType main_file_type;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String main_file_path;

    @Enumerated(EnumType.STRING)
    @Column
    private StoryType sub_file_type;
    @Column(columnDefinition = "LONGTEXT")
    private String sub_file_path;


    @Column
    private String secret_key;
    @Column
    private String nft_hash;

    @Column
    private Integer reportedCount;


    @OneToMany(mappedBy = "story")
    private List<MemberLikeStory> membersLiked = new ArrayList<>();

    @ManyToOne(targetEntity = StoryBox.class)
    @JoinColumn(name = "storybox_id")
    private StoryBox storyBox;

    @Builder
    public Story(Member member,
                 StoryType main_file_type, String main_file_path,
                 StoryType sub_file_type, String sub_file_path,
                 StoryBox storyBox
    ){
        this.member = member;
        this.storyBox = storyBox;

        this.main_file_path = main_file_path;
        this.main_file_type = main_file_type;

        this.sub_file_path = sub_file_path;
        this.sub_file_type = sub_file_type;

        this.reportedCount = 0;
    }

    public void makeNFT(String secret_key, String nft_hash){
        this.secret_key = secret_key;
        this.nft_hash = nft_hash;
    }

    public void updateReportCount(Integer reportedCount){this.reportedCount = reportedCount;}
}
