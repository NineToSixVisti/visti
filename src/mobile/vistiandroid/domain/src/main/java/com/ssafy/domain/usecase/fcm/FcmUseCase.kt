package com.ssafy.domain.usecase.fcm

import com.ssafy.domain.repository.FcmRepository
import javax.inject.Inject

class FcmUseCase @Inject constructor(
    private val repository: FcmRepository
) {
    suspend fun uploadFcmToken(fcmToken: String) {
        repository.uploadFcmToken(fcmToken)
    }
}