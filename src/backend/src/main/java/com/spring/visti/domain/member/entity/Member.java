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

    @Column
    private String profile_path;

    @Column
    private String refreshToken;

    @Column
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

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
    public Member(String email, String password, String name, String nickname, String profile_path, Role role, MemberType memberType){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profile_path = profile_path;
        this.role = role;
        this.memberType = memberType;
        this.status = true;
    }

    public void updateMemberToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void expireMember(){
        this.email = null;
        this.name = null;
        this.profile_path = null;
        this.role = null;
        this.status = false;
    }
}
