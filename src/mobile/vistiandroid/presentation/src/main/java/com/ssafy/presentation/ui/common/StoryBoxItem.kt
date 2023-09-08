package com.ssafy.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ssafy.domain.model.Story
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.White

@Composable
fun StoryBoxItem(story: Story) {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Box {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = story.downloadUrl)
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(drawableResId = R.drawable.placeholder)
                        crossfade(true)
                    }).build()
            )

            Image(
                painter = painter,
                contentDescription = "StoryBox",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .size(200.dp)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = story.author.uppercase(),
                    color = White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 8.dp,
                            ),
                        )
                        .background(DarkBackgroundColor.copy(alpha = 0.5f))
                        .padding(10.dp)
                )
            }
        }
    }
}