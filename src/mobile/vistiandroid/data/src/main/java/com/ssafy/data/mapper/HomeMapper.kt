package com.ssafy.data.mapper

import com.ssafy.data.dto.HomeStoryDto
import com.ssafy.data.dto.StoryBoxDto
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.home.HomeLastStoryBox
import com.ssafy.domain.model.home.HomeStory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun HomeStoryDto.toDomain(): HomeStory {
    return HomeStory(blind, createdAt, encryptedId, encryptedStoryBoxId, finishedAt, id, like, mainFilePath, mainFileType, storyBoxId)
}