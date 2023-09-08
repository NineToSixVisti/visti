package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.ssafy.domain.model.Image

@Composable
fun StoryLazyVerticalGrid(images: List<Image>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        this.items(images) { image ->
            StoryItem(image)
        }
    }
}
