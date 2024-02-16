package com.ssafy.presentation.ui.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.presentation.R
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.ui.common.VistiImage
import com.ssafy.presentation.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeStoryItem(homeStory: HomeStory, navController: NavController) {
    Card(
        onClick = {
            navController.navigate("${SettingNav.WebView.route}/${homeStory.encryptedId}/story") {
            }
        },
        modifier = Modifier
            .padding(end = 10.dp), shape = RoundedCornerShape(12.dp)
    ) {

        Box(modifier = Modifier.size(200.dp)) {

            VistiImage(homeStory.mainFilePath, stringResource(R.string.past_story))

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