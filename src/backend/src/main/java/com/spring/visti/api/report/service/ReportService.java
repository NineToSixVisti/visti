package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.MemberInformDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.member.dto.MemberLoginDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.report.dto.ReportBuildDTO;
import com.spring.visti.domain.report.entity.Report;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ReportService {
    BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, HttpServletRequest httpServletRequest);
    BaseResponseDTO<List<Report>> readReports(HttpServletRequest httpServletRequest);
    BaseResponseDTO<Report> readReportDetail(Long reportId, HttpServletRequest httpServletRequest);
    BaseResponseDTO<String> updateReport(Long reportId, Boolean process, HttpServletRequest httpServletRequest);


}
