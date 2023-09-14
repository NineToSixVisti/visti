package com.ssafy.domain.usecase.memberinformation

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryList
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMyStoryUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<Resource<StoryList>> = flow {
        try {
            emit(Resource.Loading<StoryList>())
            val myStoryResponse = repository.getMyStories(0, 21)
            emit(Resource.Success<StoryList>(myStoryResponse))
        } catch (e: HttpException) {
            emit(Resource.Error<StoryList>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<StoryList>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}