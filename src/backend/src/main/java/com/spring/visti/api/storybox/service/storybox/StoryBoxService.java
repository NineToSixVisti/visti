package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoryBoxService {
    BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO memberInfo, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> joinStoryBox(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, HttpServletRequest httpServletRequest);

    BaseResponseDTO<List<StoryBoxListDTO>> readMyStoryBoxes(HttpServletRequest httpServletRequest);

    BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<List<StoryBoxStoryListDTO>> readStoriesInStoryBox(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> makeStoryBoxLink(Long id, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> leaveStoryBox(Long id, HttpServletRequest httpServletRequest);

}
