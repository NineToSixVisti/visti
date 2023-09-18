package com.ssafy.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemberInformationRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : MemberInformationRepository {
    override suspend fun getMemberInformation(): Member {
        return api.getMemberInformation().detail.toDomain()
    }

    override suspend fun getMyStoryBoxes(page: Int, size: Int): StoryBoxList {
        return api.getMyStoryBoxes(page, size).detail.toDomain()
    }

    override fun getMyStories(page: Int, size: Int): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = size,
            ),
            pagingSourceFactory = {
                StoryPagingSource(api)
            }
        ).flow
    }
}