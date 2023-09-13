package com.ssafy.presentation.ui.like

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story

data class LikeListState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val error: String = ""
)

data class MemberState(
    val isLoading: Boolean = false,
    val memberSimpleInformation: Member = Member(),
    val error: String = ""
)