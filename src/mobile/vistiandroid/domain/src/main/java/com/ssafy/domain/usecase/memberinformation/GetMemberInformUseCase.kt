package com.ssafy.domain.usecase.memberinformation

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.MemberInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
            emit(Resource.Error<Member>("${e.localizedMessage} HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Member>("${e.localizedMessage} IO Error"))
        } catch (e: Exception) {
            emit(Resource.Error<Member>("${e.localizedMessage} Unknown Error"))
        }
    }
}