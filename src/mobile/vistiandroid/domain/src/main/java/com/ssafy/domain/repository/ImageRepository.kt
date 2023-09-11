package com.ssafy.domain.repository

import com.ssafy.domain.model.Image

interface ImageRepository {
    suspend fun getImages(): List<Image>
}