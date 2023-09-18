package com.ssafy.domain.usecase.memberinformation

import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMyStoryBoxUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<Resource<StoryBoxList>> = flow {
        try {
            emit(Resource.Loading())
            val myStoryBoxResponse = repository.getMyStoryBoxes(0, 5)
            emit(Resource.Success(myStoryBoxResponse))
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error("${e.localizedMessage} HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("${e.localizedMessage} IO Error"))
        } catch (e: Exception) {
            emit(Resource.Error("${e.localizedMessage} Unknown Error"))
        }
    }
}