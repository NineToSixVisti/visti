package com.ssafy.domain.repository

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.model.home.HomeStory

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    suspend fun getMyStoryBoxes(page: Int, size: Int): StoryBoxList
    suspend fun getMyStories(page: Int, size: Int): StoryList
    suspend fun getHomeMyStories(): List<HomeStory>
}