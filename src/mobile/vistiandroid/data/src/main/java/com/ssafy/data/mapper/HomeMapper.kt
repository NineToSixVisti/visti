package com.ssafy.data.mapper

import com.ssafy.data.dto.HomeStoryDto
import com.ssafy.domain.model.home.HomeStory

fun HomeStoryDto.toDomain(): HomeStory {
    return HomeStory(blind, createdAt, encryptedId, encryptedStoryBoxId, finishedAt, id, like, mainFilePath, mainFileType, storyBoxId)
}