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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.user.componet.LogInButton
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField


@Composable
fun JoinPasswordScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            text = "비밀번호를 입력해주세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        Text(
            text = "비밀번호",
            modifier = Modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinPasswordTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(hint = "비밀번호를 입력해주세요", text = joinPasswordTextFieldState) {
            joinPasswordTextFieldState = it
        }
        Text(
            text = "비밀번호 확인",
            modifier = Modifier
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinCheckPasswordTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(hint = "비밀번호를 다시 입력해주세요", text = joinCheckPasswordTextFieldState) {
            joinCheckPasswordTextFieldState = it
        }

        Box(
            modifier = Modifier
                .padding(vertical = 15.dp)
        )

        LogInButton("다음", PrimaryColor)
        Box(modifier = Modifier.padding(5.dp))

    }

}