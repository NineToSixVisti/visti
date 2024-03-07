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
    val storyBoxId: Int,
)

data class HomeLastStoryBox(
    val id: Int = -1,
    val encryptedId: String = "",
    val boxImgPath: String = "",
    val name: String = "",
    val createdAt: String = "",
    val finishAt: Long = 0L,
    val blind: Boolean = false,
)