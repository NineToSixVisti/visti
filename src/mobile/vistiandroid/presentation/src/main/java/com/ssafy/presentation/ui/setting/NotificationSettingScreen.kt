package com.ssafy.presentation.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.setting.component.BackToolbar
import com.ssafy.presentation.ui.setting.component.NotificationSwitch


@Composable
fun NotificationSettingScreen(
    navController: NavController,
) {

    var allNotification by remember { mutableStateOf(false) }
    var storyNotification by remember { mutableStateOf(true) }
    var storyBoxNotification by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            BackToolbar(text = stringResource(R.string.notification)) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 8.dp, end = 8.dp)
        ) {
            NotificationSwitch(
                stringResource(R.string.all_notification_title),
                stringResource(R.string.all_notification_content), allNotification
            ) {
                allNotification = it
            }
            NotificationSwitch(
                stringResource(R.string.story_notification_title),
                stringResource(R.string.story_notification_content), storyNotification
            ) {
                storyNotification = it
            }
            NotificationSwitch(
                stringResource(R.string.story_box_notification_title),
                stringResource(
                    R.string.story_box_notification_content
                ), storyBoxNotification
            ) {
                storyBoxNotification = it
            }
        }
    }
}
