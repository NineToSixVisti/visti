package com.ssafy.data.remote

import com.ssafy.data.dto.HomeStoryBoxResponse
import com.ssafy.data.dto.HomeStoryResponse
import com.ssafy.data.dto.MemberResponse
import com.ssafy.data.dto.StoryBoxResponse
import com.ssafy.data.dto.StoryResponse
import com.ssafy.data.dto.UserBodyDto
import com.ssafy.data.dto.UserTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VistiApi {
    @GET("/api/member/mypage")
    suspend fun getMemberInformation(): MemberResponse

    @GET("/api/story-box/mystorybox")
    suspend fun getMyStoryBoxes(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryBoxResponse

    @GET("/api/story/mystory")
    suspend fun getMyStories(@Query("page") page: Int, @Query("size") size: Int): StoryResponse

    @GET("/api/story/likedstory")
    suspend fun getLikedStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sorting_option") sortingOption: String
    ): StoryResponse

    @POST("/api/member/signin")
    suspend fun signIn(@Body userBodyDto: UserBodyDto): UserTokenResponse

    @GET("/oauth/{provider}")
    suspend fun socialSignIn(
        @Path("provider") provider: String,
        @Query("accessToken") accessToken: String
    ): UserTokenResponse

    @GET("/api/story/mainpage")
    suspend fun getHomeStories(): HomeStoryResponse

    @GET("/api/story-box/mainpage")
    suspend fun getHomeStoryBox(): HomeStoryBoxResponse


    @POST("/fcmtoken/gettoken")
    suspend fun uploadFcmToken(@Body fcmToken: String)


    @POST("/api/story-box/enter")
    suspend fun enterStoryBox(@Body storyBoxId: String)
}