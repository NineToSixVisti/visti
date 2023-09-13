package com.ssafy.presentation.ui.like

import com.ssafy.domain.model.Story

data class LikeListState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val error: String = ""
)