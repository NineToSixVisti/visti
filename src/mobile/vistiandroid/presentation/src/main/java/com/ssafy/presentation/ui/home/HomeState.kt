package com.ssafy.presentation.ui.home

import com.ssafy.domain.model.home.HomeStory

data class HomeStoryState(
    val isLoading: Boolean = false,
    val stories: List<HomeStory> = emptyList(),
    val error: String = ""
)
