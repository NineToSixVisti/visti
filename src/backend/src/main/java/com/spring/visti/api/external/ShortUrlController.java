package com.spring.visti.api.external;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.storybox.service.storybox.StoryBoxService;
import com.spring.visti.global.redis.service.UrlExpiryService;
import com.spring.visti.utils.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.spring.visti.utils.exception.ErrorCode.*;

@RestController
@RequestMapping("/short")
@RequiredArgsConstructor
@Tag(name = "ShortURL Validator", description = "숏 URL 해석용")
public class ShortUrlController {

    private final StoryBoxService storyBoxService;
    private final UrlExpiryService urlExpiryService;

    @GetMapping("/{shortenedUrls}")
    @Operation(summary = "스토리박스 숏링크 수집", description = "스토리박스에 접속가능한 링크를 제공해줍니다.")
    ResponseEntity<? extends BaseResponseDTO<String>> redirect(
            @PathVariable String shortenedUrls
    ) {
        String expandedParam = urlExpiryService.expand(shortenedUrls);

        if (expandedParam.isEmpty()){
            throw new ApiException(NO_INVITATION_STORY_BOX);
        }

        String token = extractTokenFromUrl(expandedParam);

        String email = getEmail();

        BaseResponseDTO<String> response = storyBoxService.validateStoryBoxLink(token, email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    private static String extractTokenFromUrl(String expandedUrl) {
        if (expandedUrl != null && expandedUrl.startsWith("StoryBoxTokenInfo=")) {
            return expandedUrl.substring("StoryBoxTokenInfo=".length());
        }
        throw new ApiException(NO_MATCH_STORY_BOX_ERROR);
    }
}
