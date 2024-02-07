package com.ssafy.presentation.ui.setting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ssafy.presentation.ui.setting.component.BackToolbar
import com.ssafy.presentation.ui.setting.component.DetailSettingButton
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.White


@Composable
fun InformationScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            BackToolbar(text = "정보") {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            val colorState = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            }

            DetailSettingButton("개인정보처리방침", colorState) {

            }
            DetailSettingButton("오픈소스 라이브러리", colorState) {

            }
        }
    }
}
