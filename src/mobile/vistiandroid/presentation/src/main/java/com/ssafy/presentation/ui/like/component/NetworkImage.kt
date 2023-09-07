package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ssafy.presentation.R

@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = contentDescription,
        modifier = Modifier
            .aspectRatio(1f / 1f)
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}