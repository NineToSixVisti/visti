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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.presentation.R
import com.ssafy.presentation.SignInNav
import com.ssafy.presentation.ui.common.VistiButton
import com.ssafy.presentation.ui.theme.Grey
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.user.componet.UserOutLinedTextField

@Composable
fun FindPasswordScreen(navController: NavHostController) {
    val signInScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(signInScrollState)
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.find_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        Text(
            text = stringResource(R.string.bring_temp_password_meesage),
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
            text = stringResource(R.string.email),
            modifier = Modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Grey
        )
        var joinEmailTextFieldState by remember { mutableStateOf("") }
        UserOutLinedTextField(
            hint = stringResource(R.string.input_email_message),
            text = joinEmailTextFieldState,
            keyboardType = KeyboardType.Email
        ) {
            joinEmailTextFieldState = it
        }
        Box(modifier = Modifier.padding(5.dp))
        VistiButton(stringResource(R.string.certify), PrimaryColor) {
            navController.navigate(route = SignInNav.SignIn.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}