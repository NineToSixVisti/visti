package com.ssafy.domain.model

data class Story(
    val author: String,
    val id: String,
    val downloadUrl: String,
    val isFavorite :Boolean
)