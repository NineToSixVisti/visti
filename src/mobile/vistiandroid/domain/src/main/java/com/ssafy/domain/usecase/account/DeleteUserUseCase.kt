package com.ssafy.domain.usecase.account

import android.util.Log
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "DeleteUserUseCase_kakao"
class DeleteUserUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            Log.d(TAG, "invoke: deleteMember")
            val userTokenResponse = repository.deleteMember()
            emit(Resource.Success(userTokenResponse))
        } catch (e: IOException) {
            emit(Resource.Error("Failed to connect to server \uD83D\uDE22"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error occurred"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error occurred"))
        }
    }
}