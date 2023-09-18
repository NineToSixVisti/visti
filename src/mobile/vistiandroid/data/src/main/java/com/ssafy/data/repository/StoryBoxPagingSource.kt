package com.ssafy.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.StoryBox

class StoryBoxPagingSource (
    private val api: VistiApi,
    ): PagingSource<Int, StoryBox>() {
        override fun getRefreshKey(state: PagingState<Int, StoryBox>): Int? {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryBox> {
            return try {
                val page = params.key ?: 0
                val response = api.getMyStoryBoxes(page = page, 10)

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