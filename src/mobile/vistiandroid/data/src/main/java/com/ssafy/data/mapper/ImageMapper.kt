package com.ssafy.data.mapper

import com.ssafy.data.dto.ImageDto
import com.ssafy.domain.model.Image

fun ImageDto.toDomain() : Image {
    return Image(
        author = author,
        id = id,
        downloadUrl = downloadUrl
    )
}