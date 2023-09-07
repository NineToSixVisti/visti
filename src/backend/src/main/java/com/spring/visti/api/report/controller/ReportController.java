package com.spring.visti.api.report.controller;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.report.service.ReportService;
import com.spring.visti.domain.report.dto.RequestDTO.ReportBuildDTO;
import com.spring.visti.domain.report.dto.ResponseDTO.ReportExposedDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "Report 컨트롤러", description = "신고와 관련된 API 입니다.")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/storyid/{storyId}")
    @Operation(summary = "신고 진행", description = "신고를 진행합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createReport(
            @PathVariable Long storyId,
            @RequestBody ReportBuildDTO reportInfo,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = reportService.createReport(storyId, reportInfo, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/reports")
    @Operation(summary = "처리 안된 신고 리스트업", description = "처리 안된 신고들을 리스트합니다.", tags={"관리자 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<List<ReportExposedDTO>>> readReports(
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<List<ReportExposedDTO>> response = reportService.readReports(httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/{reportId}")
    @Operation(summary = "처리 안된 신고 조회", description = "신고를 조회합니다.", tags={"관리자 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<ReportExposedDTO>> readReportDetail(
            @PathVariable Long reportId,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<ReportExposedDTO> response = reportService.readReportDetail(reportId, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{reportId}")
    @Operation(summary = "신고 처리", description = "신고를 처리합니다.", tags={"신고 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> updateReport(
            @PathVariable Long reportId,
            @RequestBody Boolean process,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = reportService.updateReport(reportId, process, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
