package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;

import java.util.List;

public interface StoryBoxService extends DefaultService {
    BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO memberInfo, String email);

    BaseResponseDTO<String> enterStoryBox(Long id, String email);

    BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, String email);

    BaseResponseDTO<List<StoryBoxListDTO>> readMyStoryBoxes(String email);

    BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, String email);

    BaseResponseDTO<List<StoryExposedDTO>> readStoriesInStoryBox(Long id, String email);

    BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, String email);

    BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email);

    BaseResponseDTO<String> generateStoryBoxLink(Long id, String email);

    BaseResponseDTO<String> validateStoryBoxLink(String token, String email);

    BaseResponseDTO<String> leaveStoryBox(Long id, String email);

}
