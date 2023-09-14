package com.ssafy.domain.repository

import com.ssafy.domain.model.StoryList

interface LikedStoryRepository {
    suspend fun getLikedStories(page: Int, size : Int, sortingOption : String): StoryList
}