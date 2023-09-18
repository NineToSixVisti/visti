package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import kotlinx.coroutines.flow.Flow

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    fun getMyStoryBoxes(size : Int): Flow<PagingData<StoryBox>>
    fun getMyStories(size : Int): Flow<PagingData<Story>>
}