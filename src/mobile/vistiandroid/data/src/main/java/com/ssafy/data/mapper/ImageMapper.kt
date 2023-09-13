package com.ssafy.data.mapper

import com.ssafy.data.dto.ImageDto
import com.ssafy.domain.model.Story

fun ImageDto.toDomain() : Story {
    return Story(
        author = author,
        id = id,
        downloadUrl = downloadUrl,
        isFavorite = true
    )
}