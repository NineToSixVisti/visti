package com.ssafy.data.mapper

import com.ssafy.data.dto.MemberDto
import com.ssafy.data.dto.MemberSimpleDto
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple

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
        email, nickname, profilePath, role, memberType, dailyStory, status, storyBoxes, stories
    )
}