package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoryBoxService extends DefaultService {
    @Transactional
    BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO storyBoxBuildDTO, String email, MultipartFile multipartFile);

    BaseResponseDTO<String> enterStoryBox(Long id, String email);

    BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, String email);

    BaseResponseDTO<List<StoryBoxExposedDTO>> readMainPageStoryBoxes(String email);

    BaseResponseDTO<Page<StoryBoxExposedDTO>> readMyStoryBoxes(Pageable pageable, String email);

    BaseResponseDTO<Page<StoryBoxExposedDTO>> readStoryBoxes(Pageable pageable, String email);

    BaseResponseDTO<Page<StoryBoxExposedDTO>> searchStoryBoxes(Pageable pageable, String email, String keyword);

    BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, String email);

    BaseResponseDTO<Page<StoryExposedDTO>> readStoriesInStoryBox(Pageable pageable, Long id, String email);

    BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, String email);

    BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email);

    BaseResponseDTO<String> generateStoryBoxLink(Long id, String email);

    BaseResponseDTO<String> validateStoryBoxLink(String token, String email);

    BaseResponseDTO<String> leaveStoryBox(Long id, String email);


}
