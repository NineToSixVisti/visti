package com.ssafy.domain.repository

import com.ssafy.domain.model.Member

interface MemberInformationRepository {
    suspend fun getMemberInformation(): Member
}