package com.ssafy.presentation.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssafy.presentation.R
import com.ssafy.presentation.SignInNav
import com.ssafy.presentation.ui.common.PasswordOutLinedTextField
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val signInScrollState = rememberScrollState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var signInEmailTextFieldState by remember { mutableStateOf("") }
    var signInPasswordTextFieldState by remember { mutableStateOf("") }
    val state by signInViewModel.userToken.collectAsState()

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) {
        it
        when {
            state.error.isNotBlank() -> {
                SideEffect {
                    CoroutineScope(Dispatchers.Main).launch {
                        snackbarHostState.showSnackbar("아이디 비밀번호가 틀렸습니다.")

                    }
                    signInViewModel.delete()
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

            state.token?.accessToken?.isNotBlank() == true -> {
                navController.navigate(route = SignInNav.Main.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
                signInViewModel.delete()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(signInScrollState)
                .padding(20.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 65.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
                alignment = Alignment.Center,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.log_in_logo_description),
                contentScale = ContentScale.FillHeight,
            )
            Text(text = stringResource(R.string.email), modifier = Modifier.padding(bottom = 5.dp))

            UserOutLinedTextField("이메일을 입력하세요", signInEmailTextFieldState, KeyboardType.Email) {
                signInEmailTextFieldState = it
            }

            Text(text = "비밀번호", modifier = Modifier.padding(top = 10.dp, bottom = 5.dp))

            PasswordOutLinedTextField("비밀번호를 입력하세요", signInPasswordTextFieldState) {
                signInPasswordTextFieldState = it
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {

                Text(
                    text = "비밀번호 찾기", color = PrimaryColor, fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(route = SignInNav.FindPassword.route)
                    }
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
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            navController.navigate(route = SignInNav.JoinEmail.route)
                        }
                    )
                }
            }


            Box(modifier = Modifier.padding(15.dp))
            SignInButton("비스티 로그인", PrimaryColor, Color.White, R.drawable.logo_white, 20.dp) {
                if (signInEmailTextFieldState.isBlank() || signInPasswordTextFieldState.isBlank()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        snackbarHostState.showSnackbar("아이디 비밀번호 입력 값을 모두 기입하세요.")
                    }
                } else {
                    signInViewModel.signIn(signInEmailTextFieldState, signInPasswordTextFieldState)
                }
            }
            Box(modifier = Modifier.padding(5.dp))
            SignInButton("카카오 로그인", Color(0xFFFDDC3F), Color.Black, R.drawable.kakao, 30.dp) {
                navController.navigate(route = SignInNav.Main.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            Box(modifier = Modifier.padding(5.dp))
            SignInButton("네이버 로그인", Color(0xFF03C75A), Color.White, R.drawable.naver, 30.dp) {
                navController.navigate(route = SignInNav.Main.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }


}


@Composable
fun SignInButton(
    text: String,
    color: Color,
    textColor: Color,
    imageId: Int,
    imageSize: Dp,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shape = RoundedCornerShape(
                    40.dp
                )
                clip = true
            }
            .background(color)
            .clickable {
                onClick()
            }
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier
                .alpha(1f)
                .padding(start = 25.dp)
                .size(imageSize),
            painter = painterResource(id = imageId),
            contentDescription = "home_logo"
        )
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        Text(text = "")
    }

}