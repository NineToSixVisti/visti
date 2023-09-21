package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.home.HomeStory
import kotlinx.coroutines.flow.Flow

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    fun getMyStoryBoxes(): Flow<PagingData<StoryBox>>
    fun getMyStories(): Flow<PagingData<Story>>

    suspend fun getHomeMyStories(): List<HomeStory>
    suspend fun getHomeMyStoryBox(): List<StoryBox>
    suspend fun getHomeLastStoryBox(): StoryBox

}