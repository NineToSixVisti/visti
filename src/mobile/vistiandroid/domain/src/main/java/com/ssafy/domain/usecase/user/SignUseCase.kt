package com.ssafy.domain.usecase.user

import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken
import com.ssafy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<UserToken>> = flow {
        try {
            emit(Resource.Loading<UserToken>())
            val userTokenResponse = repository.signIn(UserBody(email, password))
            emit(Resource.Success<UserToken>(userTokenResponse))
        } catch (e: HttpException) {
            emit(Resource.Error<UserToken>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<UserToken>("Failed to connect to server \uD83D\uDE22"))
        }catch (e: Exception){
            emit(Resource.Error<UserToken>(e.localizedMessage ?: "Error occurred"))
        }
    }
}