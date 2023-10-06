package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.Pink80

@Composable
fun ImageSection(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val image =
        if (imageUrl == null) painterResource(id = R.drawable.ic_profile) else rememberAsyncImagePainter(
            imageUrl
        )

    Image(
        painter = image,
        contentDescription = "profile",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
    )

}