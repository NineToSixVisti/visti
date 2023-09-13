package com.ssafy.data.repository

import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple
import com.ssafy.domain.repository.MemberInformationRepository
import javax.inject.Inject

class MemberInformationRepositoryImpl @Inject constructor(
    private val api: VistiApi
) : MemberInformationRepository {
    override suspend fun getMemberInformation(): Member {
        return api.getMemberInformation()
    }
}