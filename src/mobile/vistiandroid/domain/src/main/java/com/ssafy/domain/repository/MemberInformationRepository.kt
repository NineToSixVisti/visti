package com.ssafy.domain.repository

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.StoryBoxList

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
    suspend fun getMyStoryBoxes(page: Int, size : Int): StoryBoxList
}