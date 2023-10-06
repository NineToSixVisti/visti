package com.ssafy.data.mapper

import com.ssafy.data.dto.UserBodyDto
import com.ssafy.data.dto.UserTokenDto
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken

fun UserTokenDto.toDomain(): UserToken {
    return UserToken(
        accessToken, refreshToken
    )
}

fun UserBody.toDomain(): UserBodyDto {
    return UserBodyDto(
        email, password
    )
}