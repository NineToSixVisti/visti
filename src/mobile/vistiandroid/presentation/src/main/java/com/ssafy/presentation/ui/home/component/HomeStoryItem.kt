package com.ssafy.presentation.ui.home.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
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
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.loadImage
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun HomeStoryItem(homeStory: HomeStory) {
    Card(
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {

        Box(modifier = Modifier.size(200.dp)) {
            val placedHolder = if (!isSystemInDarkTheme()) {
                R.drawable.placeholder
            } else {
                R.drawable.placeholder_dark
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(homeStory.mainFilePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(placedHolder),
                contentDescription = "과거의 기록",
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (homeStory.like) {
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
}