package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.constant.Role;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.report.dto.RequestDTO.ReportBuildDTO;
import com.spring.visti.domain.report.dto.ResponseDTO.ReportExposedDTO;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.domain.report.repository.ReportRepository;
import com.spring.visti.domain.storybox.entity.Story;

import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.domain.storybox.repository.StoryRepository;

import com.spring.visti.global.jwt.service.TokenProvider;
import com.spring.visti.utils.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final StoryRepository storyRepository;


    @Override
    public BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, String email) {

        Member reporter = getMember(email);

        Story story = getStory(storyId);

        Optional<Report> checkReport = reportRepository.findByReporterAndReportedStory(reporter, story);

        if (checkReport.isPresent()){
            throw new ApiException(ALREADY_REPORTED);
        }

        Report newReport = reportInfo.toEntity(reporter, story);
        reportRepository.save(newReport);
        return new BaseResponseDTO<>("신고가 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<ReportExposedDTO> readReportDetail(Long reportId, String email) {
        Member member = getMember(email);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        Report _report = getReport(reportId);

        ReportExposedDTO report = ReportExposedDTO.of(_report.getReporter(), _report.getReportedStory(), _report);

        return new BaseResponseDTO<ReportExposedDTO>("신고를 조회합니다.", 200, report);
    }

    @Override
    public BaseResponseDTO<List<ReportExposedDTO>> readReports(String email) {
        Member member = getMember(email);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        List<Report> _reports = reportRepository.findReportByProcessed(null);

        List<ReportExposedDTO> reports = _reports.stream()
                .map(report -> ReportExposedDTO.of(report.getReporter(), report.getReportedStory(), report))
                .toList();

        return new BaseResponseDTO<List<ReportExposedDTO>>("신고된 스토리들을 확인합니다.", 200, reports);
    }


    @Override
    public BaseResponseDTO<String> updateReport(Long reportId, Boolean process, String email) {
        Member member = getMember(email);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        Report report = getReport(reportId);

        // Report 설정
        report.processReport(process);

        // Story 신고 카운팅
        Story reportStory = report.getReportedStory();
        Integer storyCount = reportStory.getReportedCount() + 1;
        reportStory.updateReportCount(storyCount);

        // Member 신고 카운팅
        Member reportedMember = reportStory.getMember();
        Integer memberCount =  reportedMember.getReportedCount() + 1;
        reportedMember.updateReportCount(memberCount);

        reportRepository.save(report);
        storyRepository.save(reportStory);
        memberRepository.save(reportedMember);

        return new BaseResponseDTO<>("신고가 처리가 완료되었습니다.", 200);
    }

    public Member getMember(String email) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isEmpty()){ throw new ApiException(NO_MEMBER_ERROR); }

        return optionalMember.get();
    }

    public Story getStory(Long storyId) {

        Optional<Story> optionalStory = storyRepository.findById(storyId);

        if (optionalStory.isEmpty()){ throw new ApiException(NO_STORY_ERROR); }

        return optionalStory.get();
    }

    public Report getReport(Long reportId) {

        Optional<Report> optionalReport = reportRepository.findById(reportId);

        if (optionalReport.isEmpty()){ throw new ApiException(NO_REPORT_ERROR); }

        return optionalReport.get();
    }

}
