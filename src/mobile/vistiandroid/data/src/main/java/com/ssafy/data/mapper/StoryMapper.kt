package com.ssafy.data.mapper

import com.ssafy.data.dto.PageableDto
import com.ssafy.data.dto.SortDto
import com.ssafy.data.dto.StoryBoxDto
import com.ssafy.data.dto.StoryBoxListDto
import com.ssafy.data.dto.StoryDto
import com.ssafy.data.dto.StoryListDto
import com.ssafy.domain.model.Pageable
import com.ssafy.domain.model.Sort
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.model.StoryList
import java.text.SimpleDateFormat
import java.util.Locale

fun StoryBoxDto.toDomain(): StoryBox {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val create = dateTimeFormat.parse(createdAt)
    val finish = dateTimeFormat.parse(finishedAt)

    // SimpleDateFormat을 사용하여 원하는 형식으로 날짜 포맷팅
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

    return StoryBox(
        id,
        encryptedId,
        boxImgPath,
        name,
        dateFormat.format(create),
        dateFormat.format(finish),
        blind
    )
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
