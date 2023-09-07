package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.entity.Story;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoryService {
    BaseResponseDTO<String> createStory(StoryBuildDTO storyInfo, String email);

    BaseResponseDTO<String> createNFT4Story(Long storyId, String email);

    BaseResponseDTO<StoryExposedDTO> readStory(Long storyId, String email);

    BaseResponseDTO<List<StoryExposedDTO>> readMyStories(String email);

    BaseResponseDTO<String> likeStory(Long storyId, String email);

    BaseResponseDTO<List<StoryExposedDTO>> readLikedStories(String email);

    BaseResponseDTO<String> deleteStory(Long storyId, String email);


}
