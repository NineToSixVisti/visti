package com.spring.visti.domain.report.entity;

import com.spring.visti.domain.common.entity.BaseEntity;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "reported_story_id")
    private Story reportedStory;

    @Column
    private String reason4report;

    @Column
    private Boolean processed;

    @Builder
    public Report(Member reporter, Story reportedStory, String reason4report, String detail) {
        this.reporter = reporter;
        this.reportedStory = reportedStory;
        this.reason4report = reason4report;
    }

    public void processReport(Boolean process) {
        this.processed = process;
    }
}
