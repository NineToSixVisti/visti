package com.ssafy.domain.repository

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.model.StoryList

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    suspend fun getMyStoryBoxes(page: Int, size : Int): StoryBoxList
    suspend fun getMyStories(page: Int, size : Int): StoryList
}