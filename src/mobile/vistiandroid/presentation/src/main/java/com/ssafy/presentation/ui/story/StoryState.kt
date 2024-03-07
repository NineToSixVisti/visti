package com.ssafy.presentation.ui.story


data class StoryState(
    val isLoading: Boolean = false,
    val accessToken: String = "",
    val error: String = "",
)