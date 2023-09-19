package com.ssafy.domain.repository

import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken

interface UserRepository {
    suspend fun signIn(userBody: UserBody): UserToken

    //suspend fun kakaoSignIn()
}