package com.ssafy.domain.usecase.memberinformation

import androidx.paging.PagingData
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyStoryUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<PagingData<StoryList>> = flow {
//        try {
//            emit(Resource.Loading<StoryList>())
//            val myStoryResponse = repository.getMyStories(0, 21)
//            emit(myStoryResponse.cachedIn())
//        } catch (e: HttpException) {
//            emit(Resource.Error("${e.localizedMessage} HTTP Error"))
//        } catch (e: IOException) {
//            emit(Resource.Error("${e.localizedMessage} IO Error"))
//        } catch (e: Exception) {
//            emit(Resource.Error("${e.localizedMessage} Unknown Error"))
//        }
    }
}