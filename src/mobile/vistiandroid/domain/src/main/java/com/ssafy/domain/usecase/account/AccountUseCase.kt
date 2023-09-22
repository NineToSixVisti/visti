package com.ssafy.domain.usecase.account

import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AccountUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val userTokenResponse = repository.deleteToken()
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