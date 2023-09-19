package com.ssafy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story
import com.ssafy.domain.repository.LikedStoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikedStoryRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : LikedStoryRepository {
    override fun getLikedStories(sortingOption: LikeSortType): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_STORY_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                LikedStoryPagingSource(api, sortingOption)
            }
        ).flow
    }

    companion object {
        const val NETWORK_STORY_PAGE_SIZE = 20
        const val NETWORK_STORY_BOX_PAGE_SIZE = 10
    }
}