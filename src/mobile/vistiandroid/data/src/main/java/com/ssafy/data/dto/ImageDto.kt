package com.ssafy.data.dto

import com.google.gson.annotations.SerializedName

data class ImageDto(
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)