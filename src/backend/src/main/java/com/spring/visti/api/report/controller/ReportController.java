package com.spring.visti.api.report.controller;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.report.service.ReportService;
import com.spring.visti.domain.report.dto.ReportBuildDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "Report 컨트롤러", description = "신고와 관련된 API 입니다.")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{storyBoxId}/{storyId}")
    @Operation(summary = "신고 진행", description = "신고를 진행합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createReport(
            @PathVariable Long storyBoxId,
            @PathVariable Long storyId,
            @RequestBody ReportBuildDTO reportInfo,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = reportService.createReport(storyBoxId, storyId, reportInfo, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
