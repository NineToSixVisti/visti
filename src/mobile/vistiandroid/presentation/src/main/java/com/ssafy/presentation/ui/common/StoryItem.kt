package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.ssafy.domain.model.Image
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun StoryItem(
    image: Image
) {
    Box {
        val isFavorite = true
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.downloadUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = image.author,
            modifier = Modifier
                .aspectRatio(1f / 1f)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Icon(
            tint = PrimaryColor,
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopEnd)
        )
    }
}