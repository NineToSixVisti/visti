package com.spring.visti.domain.member.dto.ResponseDTO;


import com.spring.visti.domain.member.constant.MemberType;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberMyInfoDTO {

    private String nickname;
    private String profilePath;

    private Role role;
    private MemberType memberType;

    private Integer dailyStory;
    private Integer stories;

    private Boolean status;

//    private Integer reportedCount;

    @Builder
    public MemberMyInfoDTO(String nickname, String profilePath, Role role, MemberType memberType,
                           Integer stories, Integer dailyStory, Boolean status){


        this.nickname = nickname;
        this.profilePath = profilePath;

        this.role= role;
        this.memberType = memberType;

        this.dailyStory = dailyStory;
        this.stories = stories;
        this.status = status;
    }

    public static MemberMyInfoDTO of(Member member){
        return MemberMyInfoDTO.builder()
                .nickname(member.getNickname())
                .profilePath(member.getProfilePath())
                .role(member.getRole())
                .memberType(member.getMemberType())
                .dailyStory(member.getDailyStory())
                .stories(member.getMemberStories().size())
                .status(member.getStatus())
                .build();
    }

}
