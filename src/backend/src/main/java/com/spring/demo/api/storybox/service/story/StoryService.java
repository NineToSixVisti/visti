package com.spring.demo.api.storybox.service.story;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.storybox.dto.story.StoryBuildDTO;
import com.spring.demo.domain.storybox.entity.Story;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoryService {
    BaseResponseDTO<String> createStory(StoryBuildDTO storyInfo, HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> createNFT4Story(Long storyId, HttpServletRequest httpServletRequest);

    BaseResponseDTO<Story> readStory(Long storyId);

    BaseResponseDTO<List<Story>> readMyStories(HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> likeStory(Long storyId, HttpServletRequest httpServletRequest);

    BaseResponseDTO<List<Story>> readLikedStories(HttpServletRequest httpServletRequest);

    BaseResponseDTO<String> deleteStory(Long storyId, HttpServletRequest httpServletRequest);
}
