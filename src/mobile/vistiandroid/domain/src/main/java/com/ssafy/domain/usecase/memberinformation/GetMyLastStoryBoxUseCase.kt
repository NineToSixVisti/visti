package com.ssafy.domain.usecase.memberinformation

import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMyLastStoryBoxUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<Resource<StoryBox>> = flow {
        try {
            emit(Resource.Loading<StoryBox>())
            val homeStoryList = repository.getHomeLastStoryBox()
            emit(Resource.Success<StoryBox>(homeStoryList))
        } catch (e: HttpException) {
            emit(Resource.Error<StoryBox>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<StoryBox>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}
