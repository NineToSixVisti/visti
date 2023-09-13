package com.ssafy.domain.model

import java.util.Date

data class Story(
    val author: String,
    val id: String,
    val downloadUrl: String,
    val isFavorite :Boolean
)

data class StoryBox(
    val id: Int = 0,
    val boxImgPath: String = "",
    val name: String = "",
    val createdAt: Date = Date(),
    val finishedAt: Date = Date(),
    val blind: Boolean = false
)

data class StoryBoxList(
    val content: List<StoryBox> = emptyList(),
    val pageable: Pageable = Pageable(),
    val last: Boolean = false,
    val totalPages: Int = 0,
    val totalElements: Int = 0,
    val size: Int = 0,
    val number: Int = 0,
    val sort: Sort = Sort(),
    val first: Boolean = false,
    val numberOfElements: Int = 0,
    val empty: Boolean = true
)

data class Sort(
    val empty: Boolean = false,
    val sorted: Boolean = false,
    val unsorted: Boolean = false
)

data class Pageable(
    val sort: Sort = Sort(),
    val offset: Int = 0,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
    val paged: Boolean = true,
    val unpaged: Boolean = false
)