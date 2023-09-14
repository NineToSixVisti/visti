package com.ssafy.presentation.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
fun FindPasswordScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "비밀번호 찾기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        Text(
            text = "임시 비밀번호를 발급하시겠습니까? 버튼을 누르면 발급된 임시 비밀번호가 이메일로 전송됩니다. ",
            modifier = Modifier
                .padding(
                    start = 25.dp, end = 25.dp,
                    bottom = 20.dp
                ),
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color = Grey
        )
        Text(
            text = "이메일",
            modifier = Modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinEmailTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(hint = "이메일을 입력해주세요.", text = joinEmailTextFieldState) {
            joinEmailTextFieldState = it
        }
        Box(modifier = Modifier.padding(5.dp))
        VistiButton("인증하기", PrimaryColor) {
            navController.navigate(route = LogInNav.LogIn.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}