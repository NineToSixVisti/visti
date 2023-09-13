package com.ssafy.data.mapper

import com.ssafy.data.dto.PageableDto
import com.ssafy.data.dto.SortDto
import com.ssafy.data.dto.StoryBoxDto
import com.ssafy.data.dto.StoryBoxListDto
import com.ssafy.domain.model.Pageable
import com.ssafy.domain.model.Sort
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.StoryBoxList
import java.text.SimpleDateFormat
import java.util.Locale

fun StoryBoxDto.toDomain(): StoryBox {
    val createdAtDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(createdAt)
    val finishedAtDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(finishedAt)
    return StoryBox(id, boxImgPath, name, createdAtDate, finishedAtDate, blind)
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
