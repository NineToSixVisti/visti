package com.ssafy.domain.usecase.memberinformation

import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHomeStoryBoxUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<Resource<List<StoryBox>>> = flow {
        try {
            emit(Resource.Loading<List<StoryBox>>())
            val homeStoryBoxList = repository.getHomeMyStoryBox()
            emit(Resource.Success<List<StoryBox>>(homeStoryBoxList))
        } catch (e: HttpException) {
            emit(Resource.Error<List<StoryBox>>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<StoryBox>>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}