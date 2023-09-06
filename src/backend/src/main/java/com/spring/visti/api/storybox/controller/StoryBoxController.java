package com.spring.visti.api.storybox.controller;


import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.storybox.StoryBoxService;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxSetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/story-box")
@RequiredArgsConstructor
@Tag(name = "Story Box 컨트롤러", description = "Story Box Controller API Document")
public class StoryBoxController {
    private final StoryBoxService storyBoxService;

    @PostMapping("/create")
    @Operation(summary = "스토리-박스 만들기", description = "스토리-박스를 만듭니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createStoryBox(
            @RequestBody StoryBoxBuildDTO storyBoxInfo,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyBoxService.createStoryBox(storyBoxInfo, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{storyBoxId}/join")
    @Operation(summary = "스토리-박스에 참여합니다.", description = "스토리 박스에 참여합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> joinStoryBox(
            @PathVariable Long storyBoxId,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyBoxService.joinStoryBox(storyBoxId, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/{storyBoxId}/setting")
    @Operation(summary = "스토리-박스 만들기", description = "스토리-박스를 만듭니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> setStoryBox(
            @PathVariable Long storyBoxId,
            @RequestBody StoryBoxSetDTO storyBoxInfo,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyBoxService.setStoryBox(storyBoxId, storyBoxInfo, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "스토리-박스 삭제", description = "스토리-박스를 나갑니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> leaveStoryBox(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyBoxService.leaveStoryBox(id, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}