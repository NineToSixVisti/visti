package com.ssafy.data.repository

import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.repository.StoryBoxRepository
import javax.inject.Inject

class StoryBoxRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : StoryBoxRepository {
    override suspend fun enterStoryBox(storyBoxId: String) {
        api.enterStoryBox(storyBoxId)
    }
}