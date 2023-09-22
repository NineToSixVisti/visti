package com.ssafy.data.repository

import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.repository.StoryRepository
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val api: VistiApi,
    private val preferenceDataSource: PreferenceDataSource
) : StoryRepository {
    override suspend fun getToken(): String {
        return preferenceDataSource.getString(PreferenceDataSource.ACCESS_TOKEN, "accessToken")!!
    }
}