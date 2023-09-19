package com.ssafy.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.LikedStoryRepositoryImpl.Companion.NETWORK_STORY_PAGE_SIZE
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story

class LikedStoryPagingSource(
    private val api: VistiApi,
    private val sortType: LikeSortType
) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        val likeSortType = when (sortType) {
            LikeSortType.DOWN -> "descend"
            LikeSortType.UP -> "ascend"
            LikeSortType.RANDOM -> "shuffle"
        }

        return try {
            val position = params.key ?: 0
            val response = api.getLikedStories(page = position, NETWORK_STORY_PAGE_SIZE, likeSortType)

            LoadResult.Page(
                data = response.detail.content,
                prevKey = null,
                nextKey = if (response.detail.last) null else response.detail.number.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}