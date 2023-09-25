package com.ssafy.domain.repository

import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken

interface MemberRepository {
    suspend fun signIn(userBody: UserBody): UserToken
    suspend fun socialSignUp(provider: String, accessToken: String): UserToken
    suspend fun deleteMember(): String
}