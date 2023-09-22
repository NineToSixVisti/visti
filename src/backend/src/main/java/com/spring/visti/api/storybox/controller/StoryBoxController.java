package com.spring.visti.api.storybox.controller;


import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.storybox.StoryBoxService;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberStoryBoxExposedDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;
import com.spring.visti.utils.exception.ApiException;
import com.spring.visti.utils.urlutils.SecurePathUtil;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.visti.utils.exception.ErrorCode.NO_MEMBER_ERROR;
import static com.spring.visti.utils.exception.ErrorCode.NO_STORY_ERROR;

@RestController
@RequestMapping("/api/story-box")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Tag(name = "Story Box 컨트롤러", description = "Story Box Controller API Document")
public class StoryBoxController {
    private final StoryBoxService storyBoxService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "스토리-박스 만들기", description = "스토리-박스를 만듭니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> createStoryBox(
             @RequestPart("storyBoxInfo") StoryBoxBuildDTO storyBoxInfo,
             @RequestPart(value = "file", required = false) MultipartFile multipartFile
            ) {
        String email = getEmail();
        BaseResponseDTO<String> response = storyBoxService.createStoryBox(storyBoxInfo, email, multipartFile);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{storyBoxIds}/enter")
    @Operation(summary = "스토리-박스에 참여합니다.", description = "스토리 박스에 참여합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> enterStoryBox(
            @PathVariable String storyBoxIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<String> response = storyBoxService.enterStoryBox(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/{storyBoxIds}/setting")
    @Operation(summary = "스토리-박스 설정 및 수정", description = "스토리-박스를 설정을 합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> setStoryBox(
            @PathVariable String storyBoxIds,
            @RequestPart("storyBoxInfo") StoryBoxSetDTO storyBoxInfo,
            @RequestPart("file") MultipartFile multipartfile
    ) throws IOException {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);

        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<String> response = storyBoxService.setStoryBox(decryptedStoryId, storyBoxInfo, email, multipartfile);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{storyBoxIds}/delete")
    @Operation(summary = "스토리-박스 나가기", description = "해당 스토리-박스를 나갑니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> leaveStoryBox(
            @PathVariable String storyBoxIds
    ) {

        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<String> response = storyBoxService.leaveStoryBox(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/lateststorybox")
    @Operation(summary = "가장 최신의 스토리박스 조회", description = "메인 페이지의 가ㅏㅇ 최신의 스토리박스를... 조회합니다....")
    public ResponseEntity<? extends BaseResponseDTO<StoryBoxExposedDTO>> readLatestStoryBoxes(

    ) {
        String email = getEmail();
        BaseResponseDTO<StoryBoxExposedDTO> response = storyBoxService.readLatestStoryBoxes(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private static final String perPageBox = "5";
    @GetMapping("/mystorybox")
    @Operation(summary = "내가 작성한 스토리 박스 조회", description = "내가 작성한 스토리 박스를 리스트업 합니다.")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryBoxExposedDTO>>> readMyStoryBoxes(
            @RequestParam(name= "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name= "size", required = false, defaultValue = perPageBox ) Integer size
    ) {
        String email = getEmail();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        BaseResponseDTO<Page<StoryBoxExposedDTO>> response = storyBoxService.readMyStoryBoxes(pageRequest, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/storybox")
    @Operation(summary = "내가 들어간 스토리 박스 조회", description = "내가 들어가 있는 스토리 박스를 리스트업 합니다.")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryBoxExposedDTO>>> readStoryBoxes(
            @RequestParam(name= "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name= "size", required = false, defaultValue = perPageBox ) Integer size
    ) {
        String email = getEmail();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        BaseResponseDTO<Page<StoryBoxExposedDTO>> response = storyBoxService.readStoryBoxes(pageRequest, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/searchstorybox")
    @Operation(summary = "스토리 박스 검색", description = "스토리박스의 name을 통해 keyword로 내가 들어간 스토리박스를 검색합니다")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryBoxExposedDTO>>> searchStoryBoxes(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = perPageBox) Integer size,
            @RequestParam(name= "keyword", required = false) String keyword
    ){
        String email = getEmail();
        BaseResponseDTO<Page<StoryBoxExposedDTO>> response = storyBoxService.searchStoryBoxes(PageRequest.of(page,size), email,keyword);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/mainpage")
    @Operation(summary = "메인페이지에 제공 될 내림차순 정렬 된 스토리 박스 조회", description = "최신 스토리 박스 제공해드립니다.")
    public ResponseEntity<? extends BaseResponseDTO<List<StoryBoxExposedDTO>>> readMainPageStoryBoxes() {
        String email = getEmail();
        BaseResponseDTO<List<StoryBoxExposedDTO>> response = storyBoxService.readMainPageStoryBoxes(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
ou

    @GetMapping("/{storyBoxIds}/info")
    @Operation(summary = "스토리 박스 기본정보 제공 - 스토 박스 최상단 부분", description = "1.블라인드여부, 2.시작 및 종료날짜, 3.스토리박스 명 제공")
    public ResponseEntity<? extends BaseResponseDTO<StoryBoxInfoDTO>> readStoryBoxInfo(
            @PathVariable String storyBoxIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<StoryBoxInfoDTO> response = storyBoxService.readStoryBoxInfo(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private static final String perPageStory = "24";
    @GetMapping("/{storyBoxIds}/story-list")
    @Operation(summary = "스토리 박스의 스토리 리스트업", description = "스토리박스 내의 스토리를 모두 리스트업 합니다.")
    public ResponseEntity<? extends BaseResponseDTO<Page<StoryExposedDTO>>> readStoryInStoryBox(
            @PathVariable String storyBoxIds,
            @RequestParam(name= "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name= "size", required = false, defaultValue = perPageStory ) Integer size
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        BaseResponseDTO<Page<StoryExposedDTO>> response = storyBoxService.readStoriesInStoryBox(pageRequest, decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxIds}/members")
    @Operation(summary = "스토리 박스 멤버들 리스트업", description = "스토리 박스 내부에 참여하고있는 멤버들을 리스트업합니다.")
    public ResponseEntity<? extends BaseResponseDTO<List<MemberStoryBoxExposedDTO>>> readMemberOfStoryBox(
            @PathVariable String storyBoxIds
    ) {

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        String email = getEmail();
        BaseResponseDTO<List<MemberStoryBoxExposedDTO>> response = storyBoxService.readMemberOfStoryBox(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxIds}/detail")
    @Operation(summary = "스토리박스 상세 정보 조회", description = "스토리박스의 상세정보를 조회합니다.")
    public ResponseEntity<? extends BaseResponseDTO<StoryBoxDetailDTO>> readStoryBoxDetail(
            @PathVariable String storyBoxIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<StoryBoxDetailDTO> response = storyBoxService.readStoryBoxDetail(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{storyBoxIds}/generate")
    @Operation(summary = "스토리박스 URL 제공", description = "스토리박스에 접속가능한 숏링크를 제공해줍니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> generateStoryBoxLink(
            @PathVariable String storyBoxIds
    ) {
        String email = getEmail();

        String isDecryptedStoryId = SecurePathUtil.decodeAndDecrypt(storyBoxIds);
        long decryptedStoryId = getDecryptedStoryBoxId(isDecryptedStoryId);

        BaseResponseDTO<String> response = storyBoxService.generateStoryBoxLink(decryptedStoryId, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

/*
    @GetMapping("/validate")
    @Operation(summary = "스토리박스 URL 제공", description = "스토리박스에 접속가능한 링크를 판단합니다.")
    public ResponseEntity<? extends BaseResponseDTO<String>> validateStoryBoxLink(
            @RequestParam String token
    ) {
        String email;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            email = ((UserDetails) authentication.getPrincipal()).getUsername();
        }else{
            email = null;
        }

        BaseResponseDTO<String> response = storyBoxService.validateStoryBoxLink(token, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
*/

    private long getDecryptedStoryBoxId(String isDecryptedStoryId){
        try {
            return Long.parseLong(isDecryptedStoryId);
        } catch (NumberFormatException e) {
            throw new ApiException(NO_STORY_ERROR);
        }
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new ApiException(NO_MEMBER_ERROR);
    }
}
