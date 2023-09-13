package com.ssafy.domain.usecase

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMemberInformUseCase @Inject constructor(
    private val repository: MemberInformationRepository
) {
    operator fun invoke(): Flow<Resource<Member>> = flow {
        try {
            emit(Resource.Loading<Member>())
            val memberInformation = repository.getMemberInformation()
            emit(Resource.Success<Member>(memberInformation))
        } catch (e: HttpException) {
            emit(Resource.Error<Member>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<Member>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}