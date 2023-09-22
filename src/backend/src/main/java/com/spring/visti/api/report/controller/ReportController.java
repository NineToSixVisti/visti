package com.spring.visti.api.report.controller;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.report.service.ReportService;
import com.spring.visti.domain.report.dto.RequestDTO.ReportBuildDTO;
import com.spring.visti.domain.report.dto.ResponseDTO.ReportExposedDTO;
import com.spring.visti.utils.exception.ApiException;
import com.spring.visti.utils.urlutils.SecurePathUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spring.visti.utils.exception.ErrorCode.NO_MEMBER_ERROR;
import static com.spring.visti.utils.exception.ErrorCode.NO_STORY_ERROR;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Tag(name = "Report 컨트롤러", description = "신고와 관련된 API 입니다.")
public class ReportController {

    private final ReportService reportService;


    @PostMapping("/storyid/{storyIds}")
    @Operation(summary = "신고 진행", description = "사용자가 신고를 진행합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> createReport(
            @PathVariable String storyIds,
            @RequestBody ReportBuildDTO reportInfo
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyIds);
        long decryptedStoryId = getDecryptedStoryId(isDecryptedStoryId);


        BaseResponseDTO<String> response = reportService.createReport(decryptedStoryId, reportInfo, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/reports")
    @Operation(summary = "처리 안된 신고 리스트업", description = "처리 안된 신고들을 리스트합니다.")
    public ResponseEntity<? extends BaseResponseDTO<List<ReportExposedDTO>>> readReports() {
        String email = getEmail();

        BaseResponseDTO<List<ReportExposedDTO>> response = reportService.readReports(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{reportIds}")
    @Operation(summary = "처리 안된 신고 조회", description = "신고를 조회합니다.")
    public ResponseEntity<? extends BaseResponseDTO<ReportExposedDTO>> readReportDetail(
            @PathVariable String reportIds
    ) {
        String email = getEmail();

        String isDecryptedReportId = SecurePathUtil.decodeAndDecrypt(reportIds);
        long decryptedReportId = getDecryptedStoryId(isDecryptedReportId);

        BaseResponseDTO<ReportExposedDTO> response = reportService.readReportDetail(decryptedReportId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{reportIds}")
    @Operation(summary = "신고 처리", description = "신고를 처리합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> updateReport(
            @PathVariable String reportIds,
            @RequestBody Boolean process
    ) {
        String email = getEmail();

        String isDecryptedReportId = SecurePathUtil.decodeAndDecrypt(reportIds);
        long decryptedReportId = getDecryptedStoryId(isDecryptedReportId);

        BaseResponseDTO<String> response = reportService.updateReport(decryptedReportId, process, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new ApiException(NO_MEMBER_ERROR);
    }

    private long getDecryptedStoryId(String isDecryptedStoryId){
        try {
            return Long.parseLong(isDecryptedStoryId);
        } catch (NumberFormatException e) {
            throw new ApiException(NO_STORY_ERROR);
        }
    }
}
