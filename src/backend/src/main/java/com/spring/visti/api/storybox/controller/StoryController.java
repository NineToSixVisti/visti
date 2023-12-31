package com.spring.visti.api.storybox.controller;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.story.StoryService;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;

import com.spring.visti.utils.exception.ApiException;
import com.spring.visti.utils.urlutils.SecurePathUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.spring.visti.utils.exception.ErrorCode.*;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Tag(name = "Story 컨트롤러", description = "Story Controller API Document")
public class StoryController {
    private final StoryService storyService;

    @PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "스토리 만들기", description = "스토리를 만듭니다. 스토리 작성 수 | 해당 스토리박스의 일원이 아닐경우 에러가 발생합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> createStory(
            @RequestPart("storyInfo") StoryBuildDTO storyInfo,
            @RequestPart(value = "file", required = false)MultipartFile file
            ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyService.createStory(storyInfo, email, file);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyIds}")
    @Operation(summary = "스토리 조회", description = "스토리를 조회합니다. 유저정보가 맞지 않거나 | 해당 스토리가 없을 경우 에러가 발생합니다.")
    public ResponseEntity<? extends BaseResponseDTO<StoryExposedDTO>> readStory(
            @PathVariable String storyIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyIds);
        long decryptedStoryId = getDecryptedStoryId(isDecryptedStoryId);

        BaseResponseDTO<StoryExposedDTO> response = storyService.readStory(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private static final String perPage = "24";
    @GetMapping("/mystory")
    @Operation(summary = "내가 작성한 스토리 조회", description = "작성한 스토리를 조회합니다.")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryExposedDTO>>> readMyStories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = perPage ) Integer size
    ) {
        String email = getEmail();
        PageRequest pageRequest = PageRequest.of(page, size, getSortOption("descend"));
        BaseResponseDTO<Page<StoryExposedDTO>> response = storyService.readMyStories(pageRequest, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/likedstory")
    @Operation(summary = "내가 좋아요한 스토리 조회", description = "좋아요한 스토리를 조회합니다. \n 셔플 종류는 1. ascend \n 2. descend \n 3.shuffle")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryExposedDTO>>> readLikedStories(
            @RequestParam(name= "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name= "size", required = false, defaultValue = perPage ) Integer size,
            @RequestParam(name = "sorting_option", defaultValue = "descend") String sorting_option
    ) {
        String email = getEmail();

        if( !sorting_option.equals("ascend") && !sorting_option.equals("descend") && !sorting_option.equals("shuffle")){
            throw new ApiException(NOT_DEFINED_SORTING_ACTION);
        }

        PageRequest pageRequest = PageRequest.of(page, size, getSortOption(sorting_option));
        BaseResponseDTO<Page<StoryExposedDTO>> response = storyService.readLikedStories(pageRequest, email);

        if (sorting_option.equals("shuffle")){
            List<StoryExposedDTO> shuffledList = new ArrayList<>(response.getDetail().getContent());
            Collections.shuffle(shuffledList);

            Page<StoryExposedDTO> shuffledPage = new PageImpl<>(shuffledList, pageRequest, response.getDetail().getTotalElements());
            response.updateDetail(shuffledPage);
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/mainpage")
    @Operation(summary = "메인페이지에 제공 될 랜덤한 스토리들", description = "셔플 된 랜텀 스토리를 제공합니다.")
    public ResponseEntity<? extends BaseResponseDTO<List<StoryExposedDTO>>> readMyStories() {
        String email = getEmail();
//        String email = "ssollida94@gmail.com";
        BaseResponseDTO<List<StoryExposedDTO>> response = storyService.readMainPageStories(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyIds}/like")
    @Operation(summary = "스토리 좋아요 | 좋아요 취소", description = "스토리를 '좋아요' 또는 '좋아요 취소'를 수행합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> likeStory(
            @PathVariable String storyIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyIds);
        long decryptedStoryId = getDecryptedStoryId(isDecryptedStoryId);

        BaseResponseDTO<String> response = storyService.likeStory(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{storyIds}")
    @Operation(summary = "스토리 삭제", description = "스토리를 삭제합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> deleteStory(
            @PathVariable String storyIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyIds);
        long decryptedStoryId = getDecryptedStoryId(isDecryptedStoryId);
//
        BaseResponseDTO<String> response = storyService.deleteStory(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new ApiException(NO_MEMBER_ERROR);
    }

    private Sort getSortOption(String sorting_option) {
        return switch (sorting_option) {
            case "ascend" -> Sort.by("createdAt").ascending();
            case "descend" -> Sort.by("createdAt").descending();
            default -> Sort.unsorted();
        };
    }

    private long getDecryptedStoryId(String isDecryptedStoryId){
        try {
            return Long.parseLong(isDecryptedStoryId);
        } catch (NumberFormatException e) {
            throw new ApiException(NO_STORY_ERROR);
        }
    }

    /*

    @Override
    public BaseResponseDTO<List<StoryExposedDTO>> readLikedStories(Pageable pageable, String email, String sorting_option) {
        Member member = getMember(email, memberRepository);
        List<MemberLikeStory> memberStories = member.getMemberLikedStories();

        // MemberStory 리스트에서 Story 리스트를 추출
        List<StoryExposedDTO> myStories = memberStories.stream()
                .map(MemberLikeStory::getStory)
                .map(LikedStory -> StoryExposedDTO.of(LikedStory, true))
                .toList();

        sortStories(myStories, sorting_option);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), myStories.size());
        Page<StoryExposedDTO> pages = new PageImpl<>(myStories.subList(start, end), pageable, myStories.size());

        return new BaseResponseDTO<List<StoryExposedDTO>>(
                "좋아요한 스토리" +pageable.getPageNumber()+ "페이지 조회가 완료되었습니다.",
                200,
                sortStories(myStories, sorting_option)
        );
    }

    @GetMapping("/mystory")
    @Operation(summary = "내가 작성한 스토리 조회", description = "작성한 스토리를 조회합니다.")
    public ResponseEntity<? extends BaseResponseDTO<List<StoryExposedDTO>>> readMyStories(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = perPage ) Integer size
    ) {
        String email = getEmail();
        BaseResponseDTO<List<StoryExposedDTO>> response = storyService.readMyStories(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    */
}
