package com.ssafy.data.dto

data class MemberSimpleDto(
    val nickname: String,
    val profilePath: String?,
    val role: String,
    val memberType: String,
    val dailyStory: Int,
    val status: Boolean
)

data class MemberDto(
    val email: String = "",
    val nickname: String = "",
    val profilePath: String? = null,
    val role: String = "",
    val memberType: String = "",
    val dailyStory: Int = 0,
    val status: Boolean = true,
    val storyBoxes: Int = 0,
    val stories: Int = 0
)