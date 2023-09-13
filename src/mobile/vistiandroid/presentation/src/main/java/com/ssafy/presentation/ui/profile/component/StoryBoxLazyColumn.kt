package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.ssafy.domain.model.Story
import com.ssafy.presentation.ui.common.StoryBoxItem

@Composable
fun StoryBoxLazyColumn(stories: List<Story>) {
    LazyColumn {
        items(stories) { image ->
            StoryBoxItem(image)
        }
    }
}