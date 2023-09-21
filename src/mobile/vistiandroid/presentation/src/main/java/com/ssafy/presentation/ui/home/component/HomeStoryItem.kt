package com.ssafy.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.Black20

@Composable
fun HomeStoryItem(homeStory: HomeStory) {
    Card(
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.size(200.dp)) {
            Image(
                painter = painterResource(id = R.drawable.temp_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(), contentDescription = "과거의 기록"
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .graphicsLayer {
                            shape = RoundedCornerShape(
                                12.dp
                            )
                            clip = true
                        }
                        .background(Black20)
                        .padding(all = 5.dp)
,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "홈화면 좋아요"
                    )
                }
            }
        }
    }
}