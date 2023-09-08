package com.spring.visti.domain.member.dto.ResponseDTO;

import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberMyInfoProfileDTO {

    private String email;
    private String nickname;
    private String profile_path;

    private Role role;
    private MemberType memberType;
//    private Integer reportedCount;

    private Integer dailyStory;

    private Boolean status;

    private Integer storyBoxes;
    private Integer stories;

    @Builder
    public MemberMyInfoProfileDTO(String email, String nickname, String profile_path,
                                  Role role, MemberType memberType, Integer dailyStory, Boolean status,
                                  Integer storyBoxes, Integer stories){

        this.email = email;
        this.nickname = nickname;
        this.profile_path = profile_path;

        this.role= role;
        this.memberType = memberType;

        this.dailyStory = dailyStory;
        this.status = status;

        this.storyBoxes = storyBoxes;
        this.stories = stories;
    }

    public static MemberMyInfoProfileDTO of(Member member){
        return MemberMyInfoProfileDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profile_path(member.getProfile_path())
                .role(member.getRole())
                .memberType(member.getMemberType())
                .dailyStory(member.getDailyStory())
                .status(member.getStatus())
                .storyBoxes(member.getStoryBoxes().size())
                .stories(member.getMemberStories().size())
                .build();
    }

}