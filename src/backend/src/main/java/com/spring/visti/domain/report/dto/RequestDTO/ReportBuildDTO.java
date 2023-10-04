package com.spring.visti.domain.report.dto.RequestDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportBuildDTO {

    private String reason4report;

    @Builder
    public ReportBuildDTO( String reason4report ){
        this.reason4report = reason4report;
    }

    public Report toEntity(Member reporter, Story reportedStory){
        return Report.builder()
                .reporter(reporter)
                .reportedStory(reportedStory)
                .reason4report(reason4report)
                .build();
    }
}
