package com.ssafy.domain.repository

interface FcmRepository {
    suspend fun uploadFcmToken(fcmToken: String)
}