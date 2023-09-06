package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.MemberInformDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.member.dto.MemberLoginDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.report.dto.ReportBuildDTO;
import com.spring.visti.global.jwt.dto.TokenDTO;
import com.spring.visti.global.redis.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
    BaseResponseDTO<String> createReport(Long storyBoxId, Long storyId, ReportBuildDTO reportInfo, HttpServletRequest httpServletRequest);
    BaseResponseDTO<String> readReportDetail(HttpServletRequest httpServletRequest);
    BaseResponseDTO<String> readReports(HttpServletRequest httpServletRequest);
    BaseResponseDTO<TokenDTO> updateReport(HttpServletRequest httpServletRequest);


}
