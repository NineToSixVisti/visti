package com.ssafy.data.remote

import com.ssafy.data.dto.MemberResponse
import com.ssafy.data.dto.StoryBoxResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VistiApi {
    @GET("/api/member/mypage")
    suspend fun getMemberInformation(): MemberResponse

    @GET("/api/story-box/mystorybox")
    suspend fun getMyStoryBoxes(@Query("page") page : Int, @Query("size") size : Int): StoryBoxResponse
}