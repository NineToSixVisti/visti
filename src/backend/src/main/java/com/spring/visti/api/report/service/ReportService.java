package com.spring.visti.api.report.service;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.report.dto.RequestDTO.ReportBuildDTO;
import com.spring.visti.domain.report.dto.ResponseDTO.ReportExposedDTO;

import java.util.List;

public interface ReportService extends DefaultService {
    BaseResponseDTO<String> createReport(Long storyId, ReportBuildDTO reportInfo, String email);
    BaseResponseDTO<List<ReportExposedDTO>> readReports(String email);
    BaseResponseDTO<ReportExposedDTO> readReportDetail(Long reportId, String email);
    BaseResponseDTO<String> updateReport(Long reportId, Boolean process, String email);


}
