package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface LikedStoryRepository {
    fun getLikedStories(size: Int, sortingOption: LikeSortType): Flow<PagingData<Story>>
}