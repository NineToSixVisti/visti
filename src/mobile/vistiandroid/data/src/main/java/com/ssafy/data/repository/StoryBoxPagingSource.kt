package com.ssafy.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.LikedStoryRepositoryImpl.Companion.NETWORK_STORY_BOX_PAGE_SIZE
import com.ssafy.domain.model.StoryBox

class StoryBoxPagingSource(
    private val api: VistiApi
) : PagingSource<Int, StoryBox>() {
    override fun getRefreshKey(state: PagingState<Int, StoryBox>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryBox> {
        return try {
            val position = params.key ?: 0
            val response = api.getMyStoryBoxes(page = position,
                NETWORK_STORY_BOX_PAGE_SIZE
            )

            LoadResult.Page(
                data = response.detail.content,
                prevKey =  null,
                nextKey = if (response.detail.last) null else position.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}