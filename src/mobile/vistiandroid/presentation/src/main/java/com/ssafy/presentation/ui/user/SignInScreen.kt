package com.ssafy.presentation.ui.user

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.PasswordOutLinedTextField
import com.ssafy.presentation.ui.theme.Kakao
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SignInScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    @ApplicationContext context: Context,
    signInViewModel: SignInViewModel = hiltViewModel(),

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
        when {
            state.error.isNotBlank() -> {
                SideEffect {
                    CoroutineScope(Dispatchers.Main).launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.fail_login_message))
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
                navController.navigate(route = MainNav.Home.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
                signInViewModel.delete()
            }
        }
        Box(modifier = Modifier.padding(it)) {
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
                Text(
                    text = stringResource(R.string.email),
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                UserOutLinedTextField(
                    stringResource(R.string.input_email_message),
                    signInEmailTextFieldState,
                    KeyboardType.Email
                ) { email ->
                    signInEmailTextFieldState = email
                }

                Text(text = stringResource(R.string.password), modifier = Modifier.padding(top = 10.dp, bottom = 5.dp))

                PasswordOutLinedTextField(
                    stringResource(R.string.input_password_message),
                    signInPasswordTextFieldState
                ) {
                    signInPasswordTextFieldState = it
                }
                //TODO 아직 기능이 안만들어져 ui만 있어 주석처리
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 5.dp)
//            ) {
//
//                Text(
//                    text = "비밀번호 찾기", color = PrimaryColor, fontSize = 12.sp,
//                    textDecoration = TextDecoration.Underline,
//                    modifier = Modifier.clickable {
//                        navController.navigate(route = SignInNav.FindPassword.route)
//                    }
//                )
//
//                Row() {
//                    Text(
//                        modifier = Modifier.padding(end = 5.dp),
//                        text = "아직 아이디가 없다면?",
//                        color = Grey, fontSize = 12.sp,
//                        textDecoration = TextDecoration.Underline
//                    )
//
//                    Text(
//                        text = "회원가입",
//                        color = PrimaryColor, fontSize = 12.sp,
//                        textDecoration = TextDecoration.Underline,
//                        modifier = Modifier.clickable {
//                            navController.navigate(route = SignInNav.JoinEmail.route)
//                        }
//                    )
//                }
//            }

                Box(modifier = Modifier.padding(15.dp))
                SignInButton(
                    stringResource(R.string.visti_login),
                    PrimaryColor,
                    Color.White,
                    R.drawable.logo_white,
                    20.dp
                ) {
                    if (signInEmailTextFieldState.isBlank() || signInPasswordTextFieldState.isBlank()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar(context.getString(R.string.error_input_id_password_message))
                        }
                    } else {
                        signInViewModel.signIn(
                            signInEmailTextFieldState,
                            signInPasswordTextFieldState
                        )
                    }
                }
                Box(modifier = Modifier.padding(5.dp))
                SignInButton(
                    stringResource(R.string.kakao_login),
                    Kakao,
                    Color.Black,
                    R.drawable.kakao,
                    30.dp
                ) {

                    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                        if (error != null) {
                            Log.e(TAG, "카카오계정으로 로그인 실패1", error)

                        } else if (token != null) {
                            Log.i(TAG, "카카오계정으로 로그인 성공1 ${token.accessToken}")
                            signInViewModel.socialSignIn("kakao", token.accessToken)
                        }
                    }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                            if (error != null) {
                                Log.e(TAG, "카카오톡으로 로그인 실패2", error)

                                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                    return@loginWithKakaoTalk
                                }

                                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                                UserApiClient.instance.loginWithKakaoAccount(
                                    context,
                                    callback = callback
                                )
                            } else if (token != null) {
                                Log.i(TAG, "카카오톡으로 로그인 성공2 ${token.accessToken}")
                                signInViewModel.socialSignIn("kakao", token.accessToken)
                            }
                        }
                    } else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context,
                            callback = callback
                        )
                    }
                }
                Box(modifier = Modifier.padding(5.dp))
                //TODO 아직 기능이 안만들어져 ui만 있어 주석처리
//            SignInButton(
//                "네이버 로그인",
//                Color(0xFF03C75A),
//                Color.White,
//                R.drawable.naver,
//                30.dp
//            ) {
//                val oauthLoginCallback = object : OAuthLoginCallback {
//                    override fun onSuccess() {
//                        // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
//                        Log.e("로그인 성공", "")
//                        Log.e("AccessToken ->", NaverIdLoginSDK.getAccessToken().toString())
//                        signInViewModel.socialSignIn(
//                            "naver",
//                            NaverIdLoginSDK.getAccessToken().toString()
//                        )
//                    }
//
//                    override fun onFailure(httpStatus: Int, message: String) {
//                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
//                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                    }
//
//                    override fun onError(errorCode: Int, message: String) {
//                        onFailure(errorCode, message)
//                    }
//                }
//                NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
//            }
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
    onClick: () -> Unit,
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