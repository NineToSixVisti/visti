package com.ssafy.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.LikedStoryRepositoryImpl.Companion.NETWORK_STORY_PAGE_SIZE
import com.ssafy.domain.model.Story

class StoryPagingSource(
    private val api: VistiApi,
) : PagingSource<Int, Story>() {

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: 0
            val response = api.getMyStories(page = page, NETWORK_STORY_PAGE_SIZE)

            LoadResult.Page(
                data = response.detail.content,
                prevKey = null,
                nextKey = if (response.detail.last) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}