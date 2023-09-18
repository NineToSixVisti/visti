package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.ui.common.StoryBoxItem

@Composable
fun StoryBoxLazyColumn(boxes: LazyPagingItems<StoryBox>) {
    LazyColumn {
        items(boxes.itemSnapshotList) { box ->
            if (box != null) {
                StoryBoxItem(box)
            }
        }
    }
}