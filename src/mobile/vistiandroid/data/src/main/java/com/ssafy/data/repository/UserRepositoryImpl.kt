package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken
import com.ssafy.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : UserRepository {
    override suspend fun signIn(userBody: UserBody): UserToken {
        return api.signIn(userBody.toDomain()).detail.toDomain()
    }
}