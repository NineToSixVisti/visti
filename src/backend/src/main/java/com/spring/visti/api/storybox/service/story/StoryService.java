package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoryService extends DefaultService {
    BaseResponseDTO<String> createStory(StoryBuildDTO storyInfo, String email);

    BaseResponseDTO<String> createNFT4Story(Long storyId, String email);

    BaseResponseDTO<StoryExposedDTO> readStory(Long storyId, String email);

    BaseResponseDTO<Page<StoryExposedDTO>> readMyStories(Pageable pageable, String email);

    BaseResponseDTO<String> likeStory(Long storyId, String email);

    BaseResponseDTO<Page<StoryExposedDTO>> readLikedStories(Pageable pageable, String email);

    BaseResponseDTO<String> deleteStory(Long storyId, String email);


}
