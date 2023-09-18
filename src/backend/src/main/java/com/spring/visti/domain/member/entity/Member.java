package com.spring.visti.domain.member.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;

import com.spring.visti.global.fcm.entity.FireBaseMessage;
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
    private Integer dailyStoryCount;

    @Column
    private Integer reportedCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column
    private String fcmToken;

//    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "member")
    private List<Story> memberStories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FireBaseMessage> messages = new ArrayList<>();

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
        this.dailyStoryCount = 0;
        this.reportedCount = 0;
        this.fcmToken = "";
    }

    public void updateMemberToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateFCMToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateReportCount(Integer reportedCount){this.reportedCount = reportedCount;}

    public void updateDailyStoryCount(Integer dailyStory){this.dailyStoryCount =dailyStory;}

    public int dailyStoryMaximum(){
        int limit;
        if (this.role.equals(Role.USER)){
            limit = 5;
        } else if (this.role.equals(Role.SUBSCRIBER)) {
            limit = 6;
        }else{ // this.role.equals(Role.ADMIN)
            limit = Integer.MAX_VALUE;
        }
        return limit;
    }

    public void withdrawMember(){
        this.email = null;
        this.name = null;
        this.profilePath = null;
        this.status = false;
        this.refreshToken = null;
    }
}
