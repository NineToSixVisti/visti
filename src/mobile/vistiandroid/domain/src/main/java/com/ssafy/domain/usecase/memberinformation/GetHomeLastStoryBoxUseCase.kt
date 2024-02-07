package com.ssafy.domain.usecase.memberinformation

import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.home.HomeLastStoryBox
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHomeLastStoryBoxUseCase @Inject constructor(
    private val repository: MemberInformationRepository,
) {
    operator fun invoke(): Flow<Resource<HomeLastStoryBox>> = flow {
        try {
            emit(Resource.Loading())
            val homeStoryList = repository.getHomeLastStoryBox()
            emit(Resource.Success(homeStoryList))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Failed to connect to server \uD83D\uDE22"))
        }
    }
}
