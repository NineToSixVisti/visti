package com.ssafy.presentation.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ssafy.presentation.R

@Composable
fun loadImage(imageUrl: String): Painter {
    val placedHolder = if (!isSystemInDarkTheme()) {
        R.drawable.placeholder_wide
    } else {
        R.drawable.placeholder_wide_dark
    }

    val painter = if (imageUrl.isNotBlank()) {
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(placedHolder)
                    crossfade(true)
                }).build()
        )
    } else {
        painterResource(id = placedHolder)
    }
    return painter
}

@Composable
fun VistiImage(imageUrl: String, description: String) {
    val placedHolder = if (!isSystemInDarkTheme()) {
        R.drawable.placeholder
    } else {
        R.drawable.placeholder_dark
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(placedHolder),
        contentDescription = description,
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}