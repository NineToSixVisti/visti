package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ssafy.presentation.R

@Composable
fun ImageSection(
    imageUrl: String?,
    modifier: Modifier = Modifier,
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