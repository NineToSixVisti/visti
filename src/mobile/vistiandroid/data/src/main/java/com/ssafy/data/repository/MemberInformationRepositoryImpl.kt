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
import com.ssafy.domain.model.home.HomeLastStoryBox
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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

    override suspend fun getHomeMyStories(): List<HomeStory> {
        return api.getHomeStories().detail.map { it.toDomain() }
    }

    override suspend fun getHomeMyStoryBox(): List<StoryBox> {
        return api.getHomeStoryBox().detail.map { it.toDomain() }
    }

    override suspend fun getHomeLastStoryBox(): HomeLastStoryBox {
        val response = api.getHomeLastStoryBox().detail

        if (response != null) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val targetCalendar = Calendar.getInstance()
            targetCalendar.time = sdf.parse(response.finishedAt) ?: Calendar.getInstance().time

            val currentCalendar = Calendar.getInstance()
            var timeDiffInMillis = targetCalendar.timeInMillis - currentCalendar.timeInMillis

            if (timeDiffInMillis < 0) {
                timeDiffInMillis = 0
            }

            return HomeLastStoryBox(
                response.id,
                response.encryptedId,
                response.boxImgPath,
                response.name,
                response.createdAt,
                timeDiffInMillis,
                response.blind
            )
        }
        return HomeLastStoryBox(finishAt=1000L)
    }
}