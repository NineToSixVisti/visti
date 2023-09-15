package com.ssafy.data.dto

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    val message: String, val status : String, val detail : MemberDto
)

data class MemberSimpleDto(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String?,
    @SerializedName("role") val role: String,
    @SerializedName("memberType") val memberType: String,
    @SerializedName("dailyStory") val dailyStory: String,
    @SerializedName("status") val status: Boolean
)

data class MemberDto(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePath") val profilePath: String?,
    @SerializedName("role") val role: String,
    @SerializedName("memberType") val memberType: String,
    @SerializedName("dailyStory") val dailyStory: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("storyBoxes") val storyBoxes: String,
    @SerializedName("stories") val stories: String
)