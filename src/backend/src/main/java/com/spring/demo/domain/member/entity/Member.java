package com.spring.demo.domain.member.entity;

import com.spring.demo.domain.common.entity.BaseEntity;
import com.spring.demo.domain.member.constant.MemberType;
import com.spring.demo.domain.member.constant.Role;
import com.spring.demo.domain.story.entity.Story;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private List<MemberStory> memberLikedStories = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String nickname, String profile_path, Role role, MemberType memberType){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profile_path = profile_path;
        this.role = role;
        this.memberType = memberType;
    }
    public void updateMemberToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
