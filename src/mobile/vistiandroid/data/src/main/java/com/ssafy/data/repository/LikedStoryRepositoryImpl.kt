package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.repository.LikedStoryRepository
import javax.inject.Inject

class LikedStoryRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : LikedStoryRepository {
    override suspend fun getLikedStories(page: Int, size: Int, sortingOption: String): StoryList {
        return api.getLikedStories(page, size, sortingOption).detail.toDomain()
    }
}