package com.ssafy.domain.repository

interface StoryRepository {
    suspend fun getToken(): String
}