package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.presentation.R
import com.ssafy.presentation.SettingNav

@Composable
fun SettingSection(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        SettingButton(imageId = R.drawable.ic_notification, text = "알림 설정") {
            navController.navigate(route = SettingNav.Notification.route)
        }
//        SettingButton(imageId = R.drawable.ic_info, text = "정보") {
//            navController.navigate(route = SettingNav.Information.route)
//        }
        SettingButton(imageId = R.drawable.ic_person, text = "계정") {
            navController.navigate(route = SettingNav.UserAccount.route)
        }
    }
}