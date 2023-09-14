package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.ui.common.StoryBoxItem

@Composable
fun StoryBoxLazyColumn(boxes: List<StoryBox>) {
    LazyColumn {
        items(boxes) { box ->
            StoryBoxItem(box)
        }
    }
}