package com.ssafy.domain.repository

import com.ssafy.domain.model.Story

interface ImageRepository {
    suspend fun getImages(): List<Story>
}