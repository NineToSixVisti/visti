package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.repository.FcmRepository
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val api: VistiApi
):FcmRepository {
    override suspend fun uploadFcmToken(fcmToken: String) {
        api.uploadFcmToken(fcmToken)
    }
}