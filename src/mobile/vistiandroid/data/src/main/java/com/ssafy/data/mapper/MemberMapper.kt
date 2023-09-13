package com.ssafy.data.mapper

import com.ssafy.data.dto.MemberSimpleDto
import com.ssafy.domain.model.MemberSimple

fun MemberSimpleDto.toDomain() : MemberSimple {
    return MemberSimple(
        nickname,
        profilePath,
        role,
        memberType,
        dailyStory,
        status
    )
}