package com.ssafy.data.repository

import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
import com.ssafy.data.local.PreferenceDataSource.Companion.REFRESH_TOKEN
import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken
import com.ssafy.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: VistiApi,
    private val preferenceDataSource: PreferenceDataSource
) : UserRepository {
    override suspend fun signIn(userBody: UserBody): UserToken {
        val response = api.signIn(userBody.toDomain()).detail.toDomain()
        preferenceDataSource.putString(ACCESS_TOKEN, response.accessToken)
        preferenceDataSource.putString(REFRESH_TOKEN, response.refreshToken)
        return response
    }

    override suspend fun socialSignUp(provider: String, accessToken: String): UserToken {
        val response = api.socialSignIn(provider, accessToken).detail.toDomain()
        preferenceDataSource.putString(ACCESS_TOKEN, response.accessToken)
        preferenceDataSource.putString(REFRESH_TOKEN, response.refreshToken)
        return response
    }
}