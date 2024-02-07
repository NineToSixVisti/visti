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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
            BackToolbar(text = "알림") {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 8.dp, end = 8.dp)
        ) {
            NotificationSwitch("모든 알림", "모든 알림이 전송됩니다.", allNotification) {
                allNotification = it
            }
            NotificationSwitch("스토리 알림", "스토리 알림이 전송됩니다.", storyNotification) {
                storyNotification = it
            }
            NotificationSwitch("스토리 박스 알림", "스토리 박스 알림이 전송됩니다.", storyBoxNotification) {
                storyBoxNotification = it
            }
        }
    }
}
