package com.ssafy.domain.repository

interface TokenRepository {
    suspend fun getToken(): String
    suspend fun deleteToken()
}