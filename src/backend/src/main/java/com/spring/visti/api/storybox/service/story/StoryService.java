package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.StoryBoxExposedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoryService extends DefaultService {

    BaseResponseDTO<String> createStory(StoryBuildDTO storyBuildDTO, String email, MultipartFile multipartFile);

    BaseResponseDTO<String> createNFT4Story(Long storyId, String email);

    BaseResponseDTO<StoryExposedDTO> readStory(Long storyId, String email);

    BaseResponseDTO<Page<StoryExposedDTO>> readMyStories(Pageable pageable, String email);

    BaseResponseDTO<List<StoryExposedDTO>> readMainPageStories(String email);

    BaseResponseDTO<String> likeStory(Long storyId, String email);

    BaseResponseDTO<Page<StoryExposedDTO>> readLikedStories(Pageable pageable, String email);

    BaseResponseDTO<String> deleteStory(Long storyId, String email);


}
