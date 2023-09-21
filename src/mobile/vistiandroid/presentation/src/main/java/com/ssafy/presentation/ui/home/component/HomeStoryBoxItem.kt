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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.Black20

@Composable
fun HomeStoryBoxItem(homeStoryBox: StoryBox) {
    Card(
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.size(200.dp)) {
            Image(
                painter = painterResource(id = R.drawable.temp_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(), contentDescription = "진행중인 기록"
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = "버니즈 9기",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                )

                Row(
                    modifier = Modifier
                        .graphicsLayer {
                            shape = RoundedCornerShape(
                                12.dp
                            )
                            clip = true
                        }
                        .background(Black20),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "2023.09.01 ~ 09.30",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)
                    )
                }
            }
        }
    }
}