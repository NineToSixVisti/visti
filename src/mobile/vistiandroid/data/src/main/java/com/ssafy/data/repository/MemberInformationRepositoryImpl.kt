package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.domain.repository.MemberInformationRepository
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

    override suspend fun getMyStories(page: Int, size: Int): StoryList {
        return api.getMyStories(page, size).detail.toDomain()
    }

    override suspend fun getHomeMyStories(): List<HomeStory> {
        return api.getHomeStories().detail.map { it.toDomain() }
    }
}