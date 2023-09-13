package com.ssafy.presentation.ui.like

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.StoryBoxList

data class LikeListState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val error: String = ""
)

data class MemberState(
    val isLoading: Boolean = false,
    val memberInformation: Member = Member(),
    val error: String = ""
)

data class MyStoryBoxState(
    val isLoading: Boolean = false,
    val boxes: StoryBoxList = StoryBoxList(),
    val error: String = ""
)