package com.ssafy.data.dto

data class UserTokenResponse(
    val detail: UserTokenDto,
    val message: String,
    val statusCode: Int,
)

data class UserTokenDto(
    val accessToken: String,
    val accessTokenExpireTime: String,
    val grantType: String,
    val refreshToken: String,
    val refreshTokenExpireTime: String,
)

data class UserBodyDto(
    val email: String,
    val password: String,
)