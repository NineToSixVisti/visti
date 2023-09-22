package com.ssafy.domain.model.user

data class UserToken(
    val accessToken: String = "",
    val refreshToken: String = ""
)
