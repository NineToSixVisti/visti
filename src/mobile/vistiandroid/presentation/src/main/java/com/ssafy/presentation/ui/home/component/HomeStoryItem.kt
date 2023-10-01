package com.ssafy.presentation.ui.home.component

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.presentation.ui.common.VistiImage
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun HomeStoryItem(homeStory: HomeStory) {
    Card(
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {

        Box(modifier = Modifier.size(200.dp)) {

            VistiImage(homeStory.mainFilePath, "과거의 기록")

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