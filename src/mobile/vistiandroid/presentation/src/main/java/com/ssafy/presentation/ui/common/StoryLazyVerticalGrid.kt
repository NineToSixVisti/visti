package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.Image

@Composable
fun StoryLazyVerticalGrid(grouped: Map<String, List<Image>>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        grouped.forEach { (title, images) ->
            header {
                Text(
                    title,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 8.dp
                    )
                )
            }

            this.items(images) { image ->
                StoryItem(image)
            }
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}
