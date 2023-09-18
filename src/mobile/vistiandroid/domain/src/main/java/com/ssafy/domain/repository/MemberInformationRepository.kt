package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBoxList
import kotlinx.coroutines.flow.Flow

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    suspend fun getMyStoryBoxes(page: Int, size : Int): StoryBoxList
    fun getMyStories(page: Int, size : Int): Flow<PagingData<Story>>
}