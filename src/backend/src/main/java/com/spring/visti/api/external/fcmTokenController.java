package com.spring.visti.api.external;


import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.global.fcm.dto.FCMToMemberDTO;
import com.spring.visti.global.fcm.service.FcmService;
import com.spring.visti.utils.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.spring.visti.utils.exception.ErrorCode.NO_INVITATION_STORY_BOX;
import static com.spring.visti.utils.exception.ErrorCode.NO_MEMBER_ERROR;

@RestController
@RequestMapping("/fcmtoken")
@RequiredArgsConstructor
@Tag(name = "FCM Token을 저장하기 위한 컨트롤러", description = "FCM Token을 저장하기 위한 컨트롤러")
public class fcmTokenController {

    private final FcmService fcmService;

    @PostMapping("/gettoken")
    @Operation(summary = "스토리박스 숏링크 수집", description = "스토리박스에 접속가능한 링크를 제공해줍니다.")
    ResponseEntity<? extends BaseResponseDTO<String>> redirect(
            @RequestBody FCMToMemberDTO fcmToMemberDTO
    ) {

        String email = getEmail();

        BaseResponseDTO<String> response = fcmService.linkFCMTokenToMember(fcmToMemberDTO, email);
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
