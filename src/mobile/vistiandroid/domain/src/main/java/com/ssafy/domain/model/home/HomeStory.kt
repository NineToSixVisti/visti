package com.ssafy.domain.model.home

data class HomeStory(
    val blind: Boolean,
    val createdAt: String,
    val encryptedId: String,
    val encryptedStoryBoxId: String,
    val finishedAt: String,
    val id: Int,
    val like: Boolean,
    val mainFilePath: String,
    val mainFileType: String,
    val storyBoxId: Int
)