package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;

import java.util.List;

public interface StoryBoxService {
    BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO memberInfo, String email);

    BaseResponseDTO<String> joinStoryBox(Long id, String email);

    BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, String email);

    BaseResponseDTO<List<StoryBoxExposedDTO>> readMyStoryBoxes(String email);

    BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, String email);

    BaseResponseDTO<List<StoryExposedDTO>> readStoriesInStoryBox(Long id, String email);

    BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, String email);

    BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email);

    BaseResponseDTO<String> makeStoryBoxLink(Long id, String email);

    BaseResponseDTO<String> leaveStoryBox(Long id, String email);

}
