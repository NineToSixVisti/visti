package com.ssafy.domain.usecase.likedstory

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.repository.LikedStoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetLikedStoryUseCase @Inject constructor(
    private val repository: LikedStoryRepository
) {
    operator fun invoke(sortType: LikeSortType): Flow<Resource<StoryList>> = flow {
        try {
            emit(Resource.Loading<StoryList>())
            val likeSortType = when (sortType) {
                LikeSortType.DOWN -> "descend"
                LikeSortType.UP -> "ascend"
                LikeSortType.RANDOM -> "shuffle"
            }
            val myStoryResponse = repository.getLikedStories(0, 21, likeSortType)
            emit(Resource.Success<StoryList>(myStoryResponse))
        } catch (e: HttpException) {
            emit(Resource.Error<StoryList>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<StoryList>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}