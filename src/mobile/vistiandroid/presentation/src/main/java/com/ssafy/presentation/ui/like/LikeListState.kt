package com.ssafy.presentation.ui.like

import com.ssafy.domain.model.Image


data class LikeListState(
    val isLoading: Boolean = false,
    val images: List<Image> = emptyList(),
    val error: String = ""
)