package com.ssafy.data.remote

import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple
import retrofit2.http.GET

interface VistiApi {
    @GET("/api/member/inform")
    suspend fun getMemberInformation(): Member
}