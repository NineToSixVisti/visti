package com.spring.visti.domain.report.dto.ResponseDTO;

import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.domain.storybox.entity.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportExposedDTO {

    private String memberNickName;
    private Long storyId;
    private String reason4report;

    @Builder
    public ReportExposedDTO(String memberNickName, Long storyId, String reason4report){
        this.memberNickName = memberNickName;
        this.storyId = storyId;
        this.reason4report = reason4report;
    }

    public static ReportExposedDTO of(Member member, Story story, Report report){
        return ReportExposedDTO.builder()
                .memberNickName(member.getNickname())
                .storyId(story.getId())
                .reason4report(report.getReason4report())
                .build();
    }

}
