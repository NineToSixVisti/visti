package com.ssafy.data.remote

import com.ssafy.data.dto.MemberResponse
import com.ssafy.data.dto.StoryBoxResponse
import com.ssafy.data.dto.StoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VistiApi {
    @GET("/api/member/mypage")
    suspend fun getMemberInformation(): MemberResponse

    @GET("/api/story-box/mystorybox")
    suspend fun getMyStoryBoxes(@Query("page") page : Int, @Query("size") size : Int): StoryBoxResponse

    @GET("/api/story/mystory")
    suspend fun getMyStories(@Query("page") page : Int, @Query("size") size : Int): StoryResponse

    @GET("/api/story/likedstory")
    suspend fun getLikedStories(@Query("page") page : Int, @Query("size") size : Int, @Query("sorting_option") sortingOption : String): StoryResponse
}