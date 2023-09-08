package com.spring.visti.api.storybox.controller;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.story.StoryService;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;

import com.spring.visti.utils.exception.ApiException;
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

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
@Tag(name = "Story 컨트롤러", description = "Story Controller API Document")
public class StoryController {
    private final StoryService storyService;

    @PostMapping("/create")
    @Operation(summary = "스토리 만들기", description = "스토리를 만듭니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createStory(
            @RequestBody StoryBuildDTO storyInfo
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyService.createStory(storyInfo, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyId}")
    @Operation(summary = "스토리 조회", description = "스토리를 조회합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<StoryExposedDTO>> readStory(
            @PathVariable Long storyId
    ) {
        String email = getEmail();
        BaseResponseDTO<StoryExposedDTO> response = storyService.readStory(storyId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/mystory")
    @Operation(summary = "내가 작성한 스토리 조회", description = "작성한 스토리를 조회합니다.", tags={"마이 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<List<StoryExposedDTO>>> readMyStories() {
        String email = getEmail();
        BaseResponseDTO<List<StoryExposedDTO>> response = storyService.readMyStories(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyId}/like")
    @Operation(summary = "스토리 좋아요 | 좋아요 취소", description = "스토리를 '좋아요' 또는 '좋아요 취소'를 수행합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> likeStory(
            @PathVariable Long storyId
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyService.likeStory(storyId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/likedstory")
    @Operation(summary = "내가 좋아요한 스토리 조회", description = "좋아요한 스토리를 조회합니다.", tags={"Nav 바"})
    public ResponseEntity<? extends BaseResponseDTO<List<StoryExposedDTO>>> readLikedStories() {
        String email = getEmail();
        BaseResponseDTO<List<StoryExposedDTO>> response = storyService.readLikedStories(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{storyId}")
    @Operation(summary = "스토리 삭제", description = "스토리를 삭제합니다.", tags={"스토리 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> deleteStory(
            @PathVariable Long storyId
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyService.deleteStory(storyId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new ApiException(NO_MEMBER_ERROR);
    }

}
