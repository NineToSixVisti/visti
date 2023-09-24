package com.ssafy.presentation.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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