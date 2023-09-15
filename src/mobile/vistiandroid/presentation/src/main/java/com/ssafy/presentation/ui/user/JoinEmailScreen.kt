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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.presentation.LogInNav
import com.ssafy.presentation.ui.common.VistiButton
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField

@Composable
fun JoinEmailScreen(navController: NavHostController) {
    val loginScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(loginScrollState)
            .padding(20.dp)
    ) {
        Text(
            text = "회원가입",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        Text(
            text = "이메일 인증을 진행해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center,
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
        UserOutLinedTextField(
            hint = "이메일을 입력해주세요.",
            text = joinEmailTextFieldState,
            KeyboardType.Email
        ) {
            joinEmailTextFieldState = it
        }
        Text(
            text = "인증번호",
            modifier = Modifier
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinNumberTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(
            hint = "인증번호를 입력해주세요",
            text = joinNumberTextFieldState,
            keyboardType = KeyboardType.Number
        ) {
            joinNumberTextFieldState = it
        }

        Text(
            text = "4:11",
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
            color = PrimaryColor,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )

        VistiButton("번호 전송", SecondaryColor) {

        }
        Box(modifier = Modifier.padding(5.dp))
        VistiButton("인증하기", PrimaryColor) {
            navController.navigate(route = LogInNav.JoinPassword.route)
        }

    }


}