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
    @Column(nullable = false, name="main_file_type")
    private StoryType mainFileType;
    @Column(nullable = false, columnDefinition = "LONGTEXT", name="main_file_path")
    private String mainFilePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_file_type")
    private StoryType subFileType;
    @Column(columnDefinition = "LONGTEXT", name="sub_file_path")
    private String subFilePath;


    @Column(name="secret_key")
    private String secretKey;
    @Column(name="nft_hash")
    private String nftHash;

    @Column
    private Integer reportedCount;


    @OneToMany(mappedBy = "story")
    private List<MemberLikeStory> membersLiked = new ArrayList<>();

    @ManyToOne(targetEntity = StoryBox.class)
    @JoinColumn(name = "storybox_id")
    private StoryBox storyBox;

    @Builder
    public Story(Member member,
                 StoryType mainFileType, String mainFilePath,
                 StoryType subFileType, String subFilePath,
                 StoryBox storyBox
    ){
        this.member = member;
        this.storyBox = storyBox;

        this.mainFileType = mainFileType;
        this.mainFilePath = mainFilePath;

        this.subFileType = subFileType;
        this.subFilePath = subFilePath;

        this.reportedCount = 0;
    }

    public void makeNFT(String secretKey, String nftHash){
        this.secretKey = secretKey;
        this.nftHash = nftHash;
    }

    public void updateReportCount(Integer reportedCount){this.reportedCount = reportedCount;}
}
