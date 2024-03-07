package com.ssafy.domain.usecase.storybox

import android.util.Log
import com.ssafy.domain.repository.StoryBoxRepository
import retrofit2.HttpException
import javax.inject.Inject

class StoryBoxUseCase @Inject constructor(
    private val repository: StoryBoxRepository,
) {
    suspend fun enterStoryBox(storyBoxId: String) {
        try {
            repository.enterStoryBox(storyBoxId)
        } catch (e: HttpException) {
            Log.e("StoryBoxUseCase", e.message())
        }
    }
}