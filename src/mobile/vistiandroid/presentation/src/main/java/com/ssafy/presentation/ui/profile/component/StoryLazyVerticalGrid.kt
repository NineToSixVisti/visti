package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.ssafy.domain.model.Story
import com.ssafy.presentation.ui.common.StoryItem

@Composable
fun StoryLazyVerticalGrid(stories: List<Story>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        this.items(stories) { image ->
            StoryItem(image)
        }
    }
}
