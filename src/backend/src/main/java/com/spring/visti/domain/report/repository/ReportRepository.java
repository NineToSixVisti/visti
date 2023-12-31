package com.spring.visti.domain.report.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository  extends JpaRepository<Report, Long> {
    Optional<Report> findByReporterAndReportedStory(Member reporter, Story reportedStory);
    List<Report> findReportByProcessed(Boolean process);
}
