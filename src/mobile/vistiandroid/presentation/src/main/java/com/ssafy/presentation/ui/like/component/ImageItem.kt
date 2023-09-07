package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.Image
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun ImageItem(image: Image) {
    Box {
        val isFavorite = true
        NetworkImage(
            url = image.downloadUrl,
            contentDescription = null
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