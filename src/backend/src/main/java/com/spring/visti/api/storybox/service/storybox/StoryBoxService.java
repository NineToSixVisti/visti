package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxInfoDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxSetDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface StoryBoxService {
    BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO memberInfo, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> joinStoryBox(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> readStoryBoxInfo(StoryBoxInfoDTO memberInfo);

    BaseResponseDTO<String> readMemberOfStoryBox(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> readMyStoryBoxes(MemberJoinDTO memberInfo);

    BaseResponseDTO<String> leaveStoryBox(Long id, HttpServletRequest httpServletRequest);

}
