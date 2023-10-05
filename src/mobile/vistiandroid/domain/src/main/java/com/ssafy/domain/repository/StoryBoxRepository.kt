package com.ssafy.domain.repository

interface StoryBoxRepository {
    suspend fun enterStoryBox(storyBoxId: String)
}