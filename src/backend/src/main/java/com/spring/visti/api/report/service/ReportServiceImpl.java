package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.constant.Role;
import com.spring.visti.domain.member.dto.MemberInformDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.member.dto.MemberLoginDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.report.dto.ReportBuildDTO;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.domain.report.repository.ReportRepository;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.repository.StoryBoxMemberRepository;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.domain.storybox.repository.StoryRepository;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.jwt.service.TokenProvider;
import com.spring.visti.global.redis.dto.AuthDTO;
import com.spring.visti.utils.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final StoryBoxRepository storyBoxRepository;
    private final StoryRepository storyRepository;
    private final TokenProvider tokenProvider;

    @Override
    public BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, HttpServletRequest httpServletRequest) {

        Member reporter = getEmail(httpServletRequest);

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
    public BaseResponseDTO<Report> readReportDetail(Long reportId, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        Report report = getReport(reportId);

        return new BaseResponseDTO<Report>("신고를 조회합니다.", 200, report);
    }

    @Override
    public BaseResponseDTO<List<Report>> readReports(HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        List<Report> reports = reportRepository.findReportByProcessed(null);

        return new BaseResponseDTO<List<Report>>("신고된 스토리들을 확인합니다.", 200, reports);
    }


    @Override
    public BaseResponseDTO<String> updateReport(Long reportId, Boolean process, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        if (!Role.ADMIN.equals(member.getRole())){ throw new ApiException(NO_AUTHORIZE_ERROR); }

        Report report = getReport(reportId);

        report.processReport(process);
        reportRepository.save(report);

        return new BaseResponseDTO<>("신고가 처리가 완료되었습니다.", 200);
    }

    public Member getEmail(HttpServletRequest httpServletRequest) {

        String email = tokenProvider.getHeaderToken(httpServletRequest, "Access");

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
