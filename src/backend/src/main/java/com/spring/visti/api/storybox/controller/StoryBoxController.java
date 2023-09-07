package com.spring.visti.api.storybox.controller;


import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.storybox.StoryBoxService;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;
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
@RequestMapping("/api/story-box")
@RequiredArgsConstructor
@Tag(name = "Story Box 컨트롤러", description = "Story Box Controller API Document")
public class StoryBoxController {
    private final StoryBoxService storyBoxService;

    @PostMapping("/create")
    @Operation(summary = "스토리-박스 만들기", description = "스토리-박스를 만듭니다.", tags={"스토리-박스 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<String>> createStoryBox(
            @RequestBody StoryBoxBuildDTO storyBoxInfo
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyBoxService.createStoryBox(storyBoxInfo, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @PostMapping("/{storyBoxId}/join")
//    @Operation(summary = "스토리-박스에 참여합니다.", description = "스토리 박스에 참여합니다.")
//    public ResponseEntity<? extends BaseResponseDTO<String>> joinStoryBox(
//            @PathVariable Long storyBoxId
//    ) {
//        String email = getEmail();
//        BaseResponseDTO<String> response = storyBoxService.joinStoryBox(storyBoxId, email);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }


    @PostMapping("/{storyBoxId}/setting")
    @Operation(summary = "스토리-박스 설정", description = "스토리-박스를 설정을 합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> setStoryBox(
            @PathVariable Long storyBoxId,
            @RequestBody StoryBoxSetDTO storyBoxInfo
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyBoxService.setStoryBox(storyBoxId, storyBoxInfo, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{storyBoxId}")
    @Operation(summary = "스토리-박스 나가기", description = "스토리-박스를 나갑니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> leaveStoryBox(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyBoxService.leaveStoryBox(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/mystorybox")
    @Operation(summary = "내가 들어간 스토리 박스 조회", description = "스토리 박스를 리스트업 합니다.", tags={"스토리-박스 페이지", "Nav 바", "마이 페이지"})
    public ResponseEntity<? extends BaseResponseDTO<List<StoryBoxListDTO>>> readMyStoryBoxes() {
        String email = getEmail();
        BaseResponseDTO<List<StoryBoxListDTO>> response = storyBoxService.readMyStoryBoxes(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/{storyBoxId}")
    @Operation(summary = "스토리 박스 기본정보 제공", description = "1.블라인드여부, 2.시작 및 종료날짜, 3.스토리박스 명 제공", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<StoryBoxInfoDTO>> readStoryBoxInfo(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<StoryBoxInfoDTO> response = storyBoxService.readStoryBoxInfo(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxId}/story-list")
    @Operation(summary = "스토리 박스의 스토리 리스트업", description = "스토리박스 내의 스토리를 모두 리스트업 합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<List<StoryExposedDTO>>> readStoryInStoryBox(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<List<StoryExposedDTO>> response = storyBoxService.readStoriesInStoryBox(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxId}/members")
    @Operation(summary = "스토리 박스 멤버들 리스트업", description = "스토리 박스 내부에 참여하고있는 멤버들을 리스트업합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<List<StoryBoxMemberListDTO>>> readMemberOfStoryBox(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<List<StoryBoxMemberListDTO>> response = storyBoxService.readMemberOfStoryBox(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxId}/detail")
    @Operation(summary = "스토리박스 상세 정보 조회", description = "스토리박스의 상세정보를 조회합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<StoryBoxDetailDTO>> readStoryBoxDetail(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<StoryBoxDetailDTO> response = storyBoxService.readStoryBoxDetail(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxId}/generate")
    @Operation(summary = "스토리박스 URL 제공", description = "스토리박스에 접속가능한 링크를 제공해줍니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> generateStoryBoxLink(
            @PathVariable Long storyBoxId
    ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyBoxService.generateStoryBoxLink(storyBoxId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/validate")
    @Operation(summary = "스토리박스 URL 제공", description = "스토리박스에 접속가능한 링크를 판단합니다.", tags={"스토리-박스 내부"})
    public ResponseEntity<? extends BaseResponseDTO<String>> validateStoryBoxLink(
            @RequestParam String token
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email;
        if (authentication != null && authentication.getPrincipal() != null) {
            email = ((UserDetails) authentication.getPrincipal()).getUsername();
        }else{
            email = null;
        }

        BaseResponseDTO<String> response = storyBoxService.validateStoryBoxLink(token, email);
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
