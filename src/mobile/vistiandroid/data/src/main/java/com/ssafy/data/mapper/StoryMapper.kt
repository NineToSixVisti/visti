package com.ssafy.data.mapper

import com.ssafy.data.dto.PageableDto
import com.ssafy.data.dto.SortDto
import com.ssafy.data.dto.StoryBoxDto
import com.ssafy.data.dto.StoryBoxListDto
import com.ssafy.data.dto.StoryDto
import com.ssafy.data.dto.StoryListDto
import com.ssafy.domain.model.MemberType
import com.ssafy.domain.model.Pageable
import com.ssafy.domain.model.Sort
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.model.StoryList

fun StoryBoxDto.toDomain(): StoryBox {
    return StoryBox(id, boxImgPath, name, createdAt, finishedAt, blind)
}

fun StoryBoxListDto.toDomain(): StoryBoxList {
    val contentList = content.map { it.toDomain() }
    return StoryBoxList(
        contentList,
        pageable.toDomain(),
        last,
        totalPages,
        totalElements,
        size,
        number,
        sort.toDomain(),
        first,
        numberOfElements,
        empty
    )
}

fun SortDto.toDomain(): Sort {
    return Sort(empty, sorted, unsorted)
}

fun PageableDto.toDomain(): Pageable {
    return Pageable(sort.toDomain(), offset, pageNumber, pageSize, paged, unpaged)
}

fun StoryDto.toDomain(): Story {
    return Story(id, storyBoxId, fileType, filePath, blind, createdAt, finishAt, like)
}

fun StoryListDto.toDomain(): StoryList {
    return StoryList(
        content,
        pageable.toDomain(),
        last,
        totalPages,
        totalElements,
        size,
        number,
        sort.toDomain(),
        first,
        numberOfElements,
        empty
    )
}
