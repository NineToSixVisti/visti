package com.ssafy.domain.usecase.memberinformation

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
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
            emit(Resource.Loading<StoryBoxList>())
            val myStoryBoxResponse = repository.getMyStoryBoxes(0, 5)
            emit(Resource.Success<StoryBoxList>(myStoryBoxResponse))
        } catch (e: HttpException) {
            emit(Resource.Error<StoryBoxList>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<StoryBoxList>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}