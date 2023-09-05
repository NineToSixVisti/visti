package com.spring.demo.api.storybox.service.storybox;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.member.dto.MemberJoinDTO;
import com.spring.demo.domain.storybox.dto.storybox.StoryBoxBuildDTO;
import com.spring.demo.domain.storybox.dto.storybox.StoryBoxInfoDTO;
import com.spring.demo.domain.storybox.dto.storybox.StoryBoxSetDTO;
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
