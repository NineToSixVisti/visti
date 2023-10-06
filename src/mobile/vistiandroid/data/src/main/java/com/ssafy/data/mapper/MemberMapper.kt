package com.ssafy.data.mapper

import com.ssafy.data.dto.MemberDto
import com.ssafy.data.dto.MemberSimpleDto
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple
import com.ssafy.domain.model.MemberType

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
        email, nickname, profilePath, mapMemberTypeToEnum(role), memberType, dailyStory, status, storyBoxes, stories
    )
}

fun mapMemberTypeToEnum(userTypeStr: String): MemberType {
    return when (userTypeStr) {
        "USER" -> MemberType.USER
        "SUBSCRIPTION" -> MemberType.SUBSCRIPTION
        "ADMIN" -> MemberType.ADMIN
        else -> throw IllegalArgumentException("알 수 없는 사용자 유형: $userTypeStr")
    }
}