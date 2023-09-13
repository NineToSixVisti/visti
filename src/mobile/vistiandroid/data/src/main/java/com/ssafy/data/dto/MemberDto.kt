package com.ssafy.data.dto

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    val message: String, val status : String, val detail : MemberDto
)
data class MemberSimpleDto(
    val nickname: String,
    val profilePath: String?,
    val role: String,
    val memberType: String,
    val dailyStory: String,
    val status: Boolean
)

data class MemberDto(
    val email: String,
    val nickname: String,
    val profilePath: String?,
    val role: String,
    val memberType: String,
    val dailyStory: String,
    val status: Boolean,
    val storyBoxes: String,
    val stories: String
)