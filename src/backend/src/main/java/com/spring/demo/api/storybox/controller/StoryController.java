package com.spring.demo.api.storybox.controller;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.api.storybox.service.story.StoryService;
import com.spring.demo.domain.storybox.dto.story.StoryBuildDTO;
import com.spring.demo.domain.storybox.entity.Story;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/story")
@RequiredArgsConstructor
@Tag(name = "Story 컨트롤러", description = "Story Controller API Document")
public class StoryController {
    private final StoryService storyService;

    @PostMapping("/create")
    @Operation(summary = "스토리 만들기", description = "스토리를 만듭니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createStory(
            @RequestBody StoryBuildDTO storyInfo,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyService.createStory(storyInfo, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyId}")
    @Operation(summary = "스토리 조회", description = "스토리를 조회합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<Story>> readStory(
            @PathVariable Long storyId
    ) {
        BaseResponseDTO<Story> response = storyService.readStory(storyId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/mystory")
    @Operation(summary = "내가 작성한 스토리 조회", description = "작성한 스토리를 조회합니다.", tags={"마이페이지"})
    public ResponseEntity<? extends BaseResponseDTO<List<Story>>> readMyStories(
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<List<Story>> response = storyService.readMyStories(httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}/like")
    @Operation(summary = "스토리 좋아요 | 좋아요 취소", description = "스토리를 '좋아요' 또는 '좋아요 취소'를 수행합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> likeStory(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyService.likeStory(id, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/likedstory")
    @Operation(summary = "내가 좋아요한 스토리 조회", description = "좋아요한 스토리를 조회합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<List<Story>>> readLikedStories(
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<List<Story>> response = storyService.readLikedStories(httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "스토리 삭제", description = "스토리를 삭제합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> deleteStory(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) {
        BaseResponseDTO<String> response = storyService.deleteStory(id, httpServletRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}