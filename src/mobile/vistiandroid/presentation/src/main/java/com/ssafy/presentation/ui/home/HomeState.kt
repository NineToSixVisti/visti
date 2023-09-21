package com.ssafy.presentation.ui.home

import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.home.HomeStory

data class HomeStoryState(
    val isLoading: Boolean = false,
    val stories: List<HomeStory> = emptyList(),
    val error: String = ""
)

data class HomeStoryBoxState(
    val isLoading: Boolean = false,
    val storyBoxList: List<StoryBox> = emptyList(),
    val error: String = ""
)
data class HomeLastStoryBoxState(
    val isLoading: Boolean = false,
    val storyBox: StoryBox = StoryBox(),
    val error: String = ""
)

