package com.ssafy.domain.usecase.likedstory

import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.repository.LikedStoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
            emit(Resource.Error("${e.localizedMessage} HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("${e.localizedMessage} IO Error"))
        } catch (e: Exception) {
            emit(Resource.Error("${e.localizedMessage} Unknown Error"))
        }
    }
}