package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.report.dto.ReportBuildDTO;
import com.spring.visti.domain.report.entity.Report;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReportService {
    BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, HttpServletRequest httpServletRequest);
    BaseResponseDTO<List<Report>> readReports(HttpServletRequest httpServletRequest);
    BaseResponseDTO<Report> readReportDetail(Long reportId, HttpServletRequest httpServletRequest);
    BaseResponseDTO<String> updateReport(Long reportId, Boolean process, HttpServletRequest httpServletRequest);


}
