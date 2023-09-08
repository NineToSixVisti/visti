package com.ssafy.data.remote

import com.ssafy.data.dto.ImageDto
import retrofit2.http.GET

interface PicsumApi {

    @GET("/v2/list")
    suspend fun getImages(): List<ImageDto>
}