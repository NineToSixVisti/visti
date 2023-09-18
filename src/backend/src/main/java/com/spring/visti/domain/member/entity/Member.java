package com.spring.visti.domain.member.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column(columnDefinition = "LONGTEXT", name="profile_path")
    private String profilePath;

    @Column
    private String refreshToken;

    @Column
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Role role;

    @Column
    private Integer dailyStory;

    @Column
    private Integer reportedCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

//    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "member")
    private List<Story> memberStories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberLikeStory> memberLikedStories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<StoryBoxMember> storyBoxes = new ArrayList<>();

    @Builder
    public Member(String email, String password, String nickname, String profilePath, Role role, MemberType memberType){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.role = role;
        this.memberType = memberType;
        this.status = true;
        this.dailyStory = 0;
        this.reportedCount = 0;
    }

    public void updateMemberToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String password){
        this.password = password;
    }
    public void updateProfile(String email, String nickname, String profilePath){
        this.email = email;
        this.nickname = nickname;
        this.profilePath = profilePath;
    }
    public void updateReportCount(Integer reportedCount){this.reportedCount = reportedCount;}

    public boolean dailyStoryCount(){
        Integer limit;

        if (this.role.equals(Role.USER)){
            limit = 5;

            if (this.dailyStory >= limit){
                return false;
            }

        } else if (this.role.equals(Role.SUBSCRIBER)) {
            limit = 6;

            if (this.dailyStory >= limit){
                return false;
            }
        }
        this.dailyStory += 1;
        return true;
    }

    public void withdrawMember(){
        this.email = null;
        this.name = null;
        this.profilePath = null;
        this.status = false;
        this.refreshToken = null;
    }
}
