package com.ssafy.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.ui.common.VistiImage
import com.ssafy.presentation.ui.theme.Black20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeStoryBoxItem(homeStoryBox: StoryBox, navController: NavController) {
    Card(
        onClick = {
            navController.navigate("${SettingNav.WebView.route}/${homeStoryBox.encryptedId}/storyBox") {
            }
        },
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {

        Box(modifier = Modifier.size(200.dp)) {

            VistiImage(homeStoryBox.boxImgPath, "진행중인 기록")

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = homeStoryBox.name,
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
                        text = "${homeStoryBox.createdAt} ~ ${homeStoryBox.finishAt}",
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