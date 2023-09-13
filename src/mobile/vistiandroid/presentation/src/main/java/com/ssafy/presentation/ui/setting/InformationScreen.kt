package com.ssafy.presentation.ui.setting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssafy.presentation.ui.profile.ProfileViewModel
import com.ssafy.presentation.ui.setting.component.BackToolbar
import com.ssafy.presentation.ui.setting.component.DetailSettingButton
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.White


@Composable
fun InformationScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    when {
        state.error.isNotBlank() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error)
            }
        }

        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Scaffold(
                topBar = {
                    BackToolbar(text = "정보")
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
    }
}