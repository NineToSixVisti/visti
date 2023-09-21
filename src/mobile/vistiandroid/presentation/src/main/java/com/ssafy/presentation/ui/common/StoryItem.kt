package com.ssafy.presentation.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ssafy.domain.model.Story
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun StoryItem(
    story: Story
) {
    Box {
        val placedHolder = if (!isSystemInDarkTheme()) {
            R.drawable.placeholder
        } else {
            R.drawable.placeholder_dark
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(story.mainFilePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(placedHolder),
            contentDescription = story.createdAt,
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (story.like) {
            Icon(
                tint = PrimaryColor,
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
            )
        }

    }
}