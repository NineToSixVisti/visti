package com.ssafy.data.repository

import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource,
) : TokenRepository {
    override suspend fun getToken(): String {
        return preferenceDataSource.getString(PreferenceDataSource.ACCESS_TOKEN, "accessToken")!!
    }

    override suspend fun deleteToken() {
        return preferenceDataSource.remove(PreferenceDataSource.ACCESS_TOKEN)
    }
}