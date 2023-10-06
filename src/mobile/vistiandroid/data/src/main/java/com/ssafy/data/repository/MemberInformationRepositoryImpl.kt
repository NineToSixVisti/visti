package com.ssafy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.LikedStoryRepositoryImpl.Companion.NETWORK_STORY_BOX_PAGE_SIZE
import com.ssafy.data.repository.LikedStoryRepositoryImpl.Companion.NETWORK_STORY_PAGE_SIZE
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemberInformationRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : MemberInformationRepository {
    override suspend fun getMemberInformation(): Member {
        return api.getMemberInformation().detail.toDomain()
    }

    override fun getMyStoryBoxes(): Flow<PagingData<StoryBox>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_STORY_BOX_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                StoryBoxPagingSource(api)
            }
        ).flow
    }

    override fun getMyStories(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_STORY_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                StoryPagingSource(api)
            }
        ).flow
    }
}