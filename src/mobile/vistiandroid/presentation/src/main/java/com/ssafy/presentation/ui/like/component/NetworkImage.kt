package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.ssafy.presentation.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        val painter = rememberImagePainter(
            data = url,
            builder = {
                placeholder(drawableResId = R.drawable.ic_launcher_background)
                crossfade(true)
            }
        )

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}