package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.entity.Story;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoryService {
    BaseResponseDTO<String> createStory(StoryBuildDTO storyInfo, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> createNFT4Story(Long storyId, HttpServletRequest httpServletRequest);

    BaseResponseDTO<StoryExposedDTO> readStory(Long storyId);

    BaseResponseDTO<List<StoryExposedDTO>> readMyStories(HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> likeStory(Long storyId, HttpServletRequest httpServletRequest);

    BaseResponseDTO<List<StoryExposedDTO>> readLikedStories(HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> deleteStory(Long storyId, HttpServletRequest httpServletRequest);


}
