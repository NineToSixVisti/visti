package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.ssafy.domain.model.Story
import com.ssafy.presentation.ui.common.StoryItem

@Composable
fun StoryLazyVerticalGrid(stories: LazyPagingItems<Story>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        this.items(stories.itemCount) { index ->
            val image = stories[index]
            if (image != null) {
                StoryItem(image)
            }
        }
    }
}
