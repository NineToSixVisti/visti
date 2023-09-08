package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.PicsumApi
import com.ssafy.domain.model.Story
import com.ssafy.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: PicsumApi
) : ImageRepository {
    override suspend fun getImages(): List<Story> {
        return api.getImages().map { it.toDomain() }
    }
}