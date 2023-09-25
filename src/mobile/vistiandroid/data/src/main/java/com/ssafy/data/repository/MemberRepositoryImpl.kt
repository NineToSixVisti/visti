package com.ssafy.data.repository

import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
import com.ssafy.data.local.PreferenceDataSource.Companion.REFRESH_TOKEN
import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken
import com.ssafy.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val api: VistiApi,
    private val preferenceDataSource: PreferenceDataSource
) : MemberRepository {
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

    override suspend fun deleteMember(): String {
        val response = api.signOut().detail
        preferenceDataSource.remove(ACCESS_TOKEN)
        preferenceDataSource.remove(REFRESH_TOKEN)
        return response
    }
}