package com.ssafy.presentation.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.presentation.LogInNav
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavHostController) {
    val loginScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(loginScrollState)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 65.dp)
                .height(100.dp)
                .fillMaxWidth(),
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.log_in_logo_description),
            contentScale = ContentScale.FillHeight,
        )
        Text(text = stringResource(R.string.email), modifier = Modifier.padding(bottom = 5.dp))
        var loginEmailTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField("이메일을 입력하세요", loginEmailTextFieldState) {
            loginEmailTextFieldState = it
        }

        Text(text = "비밀번호", modifier = Modifier.padding(top = 10.dp, bottom = 5.dp))
        var loginPasswordTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField("비밀번호를 입력하세요", loginPasswordTextFieldState) {
            loginPasswordTextFieldState = it
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Text(
                text = "비밀번호 찾기", color = PrimaryColor, fontSize = 12.sp,
                textDecoration = TextDecoration.Underline
            )
            Row() {
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "아직 아이디가 없다면?",
                    color = Grey, fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = "회원가입",
                    color = PrimaryColor, fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        Box(modifier = Modifier.padding(15.dp))
        LogInVisti {
            navController.navigate(route = LogInNav.Main.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
        Box(modifier = Modifier.padding(5.dp))
        LogInKaKao()
        Box(modifier = Modifier.padding(5.dp))
        LogInNaver()

    }
}

@Composable
fun LogInVisti(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        40.dp
                    )
                    clip = true
                }
                .background(PrimaryColor)
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                modifier = Modifier
                    .alpha(1f)
                    .padding(20.dp, 16.dp, 0.dp, 16.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "home_logo"
            )
            Text(
                text = "비스티 로그인",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "")
        }
    }

}

@Composable
fun LogInKaKao() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shape = RoundedCornerShape(
                    40.dp
                )
                clip = true
            }
            .background(Color(0xFFFEE500))
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier
                .alpha(1f)
                .padding(20.dp, 16.dp, 0.dp, 16.dp)
                .size(20.dp),
            painter = painterResource(id = R.drawable.kakao),
            contentDescription = "home_logo"
        )
        Text(
            text = "카카오 로그인",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = "")
    }
}

@Composable
fun LogInNaver() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shape = RoundedCornerShape(
                    40.dp
                )
                clip = true
            }
            .background(Color(0xFF03C75A))
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier
                .alpha(1f)
                .padding(20.dp, 16.dp, 0.dp, 16.dp)
                .size(20.dp),
            painter = painterResource(id = R.drawable.naver),
            contentDescription = "home_logo"
        )
        Text(
            text = "네이버 로그인",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = "")
    }
}