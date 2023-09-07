package com.spring.visti.api.report.service;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.report.dto.RequestDTO.ReportBuildDTO;
import com.spring.visti.domain.report.dto.ResponseDTO.ReportExposedDTO;
import com.spring.visti.domain.report.entity.Report;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReportService {
    BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, HttpServletRequest httpServletRequest);
    BaseResponseDTO<List<ReportExposedDTO>> readReports(HttpServletRequest httpServletRequest);
    BaseResponseDTO<ReportExposedDTO> readReportDetail(Long reportId, HttpServletRequest httpServletRequest);
    BaseResponseDTO<String> updateReport(Long reportId, Boolean process, HttpServletRequest httpServletRequest);


}
