package com.ssafy.presentation.ui.setting

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kakao.sdk.user.UserApiClient
import com.ssafy.presentation.SignInNav
import com.ssafy.presentation.ui.common.VistiDialog
import com.ssafy.presentation.ui.setting.component.BackToolbar
import com.ssafy.presentation.ui.setting.component.DetailSettingButton
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.theme.White

@Composable
fun UserAccountScreen(
    navController: NavController, viewModel: UserAccountViewModel = hiltViewModel(),
) {
    val signOutState = remember { mutableStateOf(false) }
    val logOutState = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        BackToolbar(text = "정보") {
            navController.popBackStack()
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            val colorState = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            }

//            DetailSettingButton("프로필 편집", colorState) {
//
//            }
//            DetailSettingButton("비밀번호 변경", colorState) {
//
//            }
            DetailSettingButton("계정 로그아웃", PrimaryColor) {
                logOutState.value = true
            }
            DetailSettingButton("계정 회원탈퇴", PrimaryColor) {
                signOutState.value = true
            }

            val isDarkTheme = isSystemInDarkTheme()

            if (logOutState.value) {
                VistiDialog(
                    onDismissRequest = { logOutState.value = false },
                    onConfirmation = {
                        logOutState.value = false
                        viewModel.removeToken()

                        navController.navigate(SignInNav.SignIn.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    "로그아웃하시겠습니까?",
                    isDarkTheme
                )
            }

            if (signOutState.value) {
                VistiDialog(
                    onDismissRequest = { signOutState.value = false },
                    onConfirmation = {
                        signOutState.value = false

                        UserApiClient.instance.unlink { error ->
                            if (error != null) {
                                // 에러가 발생한 경우 처리
                                Log.d("ohhuiju", "회원 탈퇴 실패: $error")
                            } else {
                                // 회원 탈퇴가 성공한 경우 처리
                                Log.d("ohhuiju","카카오 소셜 로그인으로 연결된 계정을 회원 탈퇴했습니다.")

                                viewModel.deleteUser()

                                navController.navigate(SignInNav.SignIn.route) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }

                                viewModel.removeToken()
                            }
                        }
                    },
                    "회원 탈퇴하시겠습니까?",
                    isDarkTheme
                )
            }
        }
    }
}
