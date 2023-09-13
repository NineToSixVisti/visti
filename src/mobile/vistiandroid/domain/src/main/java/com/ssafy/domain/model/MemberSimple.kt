package com.ssafy.domain.model

data class MemberSimple(
    val nickname: String = "",
    val profilePath: String? = null,
    val role: String = "",
    val memberType: String = "",
    val dailyStory: Int = 0,
    val status: Boolean = true
)

data class Member(
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
