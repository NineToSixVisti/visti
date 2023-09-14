package com.ssafy.data.mapper

import com.ssafy.data.dto.MemberDto
import com.ssafy.data.dto.MemberSimpleDto
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple
import com.ssafy.domain.model.UserType

fun MemberSimpleDto.toDomain(): MemberSimple {
    return MemberSimple(
        nickname,
        profilePath,
        role,
        memberType,
        dailyStory,
        status
    )
}

fun MemberDto.toDomain(): Member {
    return Member(
        email, nickname, profilePath, mapStringToEnum(role), memberType, dailyStory, status, storyBoxes, stories
    )
}

fun mapStringToEnum(userTypeStr: String): UserType {
    return when (userTypeStr) {
        "USER" -> UserType.USER
        "SUBSCRIPTION" -> UserType.SUBSCRIPTION
        "ADMIN" -> UserType.ADMIN
        else -> throw IllegalArgumentException("알 수 없는 사용자 유형: $userTypeStr")
    }
}