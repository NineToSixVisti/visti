package com.ssafy.data.dto

import com.google.gson.annotations.SerializedName
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBoxList


data class StoryResponse(
    val message: String, val status: String, val detail: StoryListDto
)

data class StoryDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("storyBoxId") val storyBoxId: Int = 0,
    @SerializedName("encryptedId") val encryptedId: String = "",
    @SerializedName("mainFileType") val fileType: String = "",
    @SerializedName("mainFilePath") val filePath: String = "",
    @SerializedName("blind") val blind: Boolean = false,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("finishAt") val finishAt: String = "",
    @SerializedName("like") val like: Boolean = false
)

data class StoryListDto(
    @SerializedName("content") val content: List<Story> = emptyList(),
    @SerializedName("pageable") val pageable: PageableDto = PageableDto(),
    @SerializedName("last") val last: Boolean = false,
    @SerializedName("totalPages") val totalPages: Int = 0,
    @SerializedName("totalElements") val totalElements: Int = 0,
    @SerializedName("size") val size: Int = 0,
    @SerializedName("number") val number: Int = 0,
    @SerializedName("sort") val sort: SortDto = SortDto(),
    @SerializedName("first") val first: Boolean = false,
    @SerializedName("numberOfElements") val numberOfElements: Int = 0,
    @SerializedName("empty") val empty: Boolean = true
)

data class StoryBoxResponse(
    val message: String, val status: String, val detail: StoryBoxList
)

data class StoryBoxDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("encryptedId") val encryptedId: String = "",
    @SerializedName("boxImgPath") val boxImgPath: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("finishedAt") val finishedAt: String = "",
    @SerializedName("blind") val blind: Boolean = false
)

data class StoryBoxListDto(
    @SerializedName("content") val content: List<StoryBoxDto> = emptyList(),
    @SerializedName("pageable") val pageable: PageableDto = PageableDto(),
    @SerializedName("last") val last: Boolean = false,
    @SerializedName("totalPages") val totalPages: Int = 0,
    @SerializedName("totalElements") val totalElements: Int = 0,
    @SerializedName("size") val size: Int = 0,
    @SerializedName("number") val number: Int = 0,
    @SerializedName("sort") val sort: SortDto = SortDto(),
    @SerializedName("first") val first: Boolean = false,
    @SerializedName("numberOfElements") val numberOfElements: Int = 0,
    @SerializedName("empty") val empty: Boolean = true
)

data class SortDto(
    @SerializedName("empty") val empty: Boolean = false,
    @SerializedName("sorted") val sorted: Boolean = false,
    @SerializedName("unsorted") val unsorted: Boolean = false
)

data class PageableDto(
    @SerializedName("sort") val sort: SortDto = SortDto(),
    @SerializedName("offset") val offset: Int = 0,
    @SerializedName("pageNumber") val pageNumber: Int = 0,
    @SerializedName("pageSize") val pageSize: Int = 5,
    @SerializedName("paged") val paged: Boolean = true,
    @SerializedName("unpaged") val unpaged: Boolean = false
)