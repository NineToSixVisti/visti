package com.ssafy.presentation.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.presentation.LogInNav
import com.ssafy.presentation.ui.common.VistiButton
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField


@Composable
fun JoinNickNameScreen(navController: NavHostController) {
    val loginScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(loginScrollState)
            .padding(20.dp)
    ) {
        Text(
            text = "닉네임 설정",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        Text(
            text = "닉네임을 입력해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        Text(
            text = "닉네임",
            modifier = Modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinNickNameTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(
            hint = "닉네임을 입력해주세요.",
            text = joinNickNameTextFieldState,
            keyboardType = KeyboardType.Text
        ) {
            joinNickNameTextFieldState = it
        }

        Box(
            modifier = Modifier
                .padding(vertical = 15.dp)
        )


        VistiButton("다음", PrimaryColor) {
            navController.navigate(route = LogInNav.JoinAgree.route)
        }
        Box(modifier = Modifier.padding(5.dp))

    }
}