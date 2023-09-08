package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ssafy.presentation.R

@Composable
fun MyPageProfileImg(profileImage: String?) {
    Surface(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileImage)
                .error(R.drawable.placeholder_profile)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp).clip(CircleShape),
            contentDescription = "프로필 이미지"
        )
    }
}