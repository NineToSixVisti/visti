package com.ssafy.data.dto

data class HomeStoryResponse(
    val detail: List<HomeStoryDto>,
    val message: String,
    val statusCode: Int
)

data class HomeStoryDto(
    val blind: Boolean,
    val createdAt: String,
    val encryptedId: String,
    val encryptedStoryBoxId: String,
    val finishedAt: String,
    val id: Int,
    val like: Boolean,
    val mainFilePath: String,
    val mainFileType: String,
    val member: Member,
    val storyBoxId: Int
)

data class Member(
    val nickname: String,
    val profilePath: String,
    val status: Boolean
)