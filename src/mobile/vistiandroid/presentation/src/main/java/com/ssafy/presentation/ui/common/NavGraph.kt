package com.ssafy.presentation.ui.common

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.presentation.SignInNav
import com.ssafy.presentation.MainScreen
import com.ssafy.presentation.ui.user.FindPasswordScreen
import com.ssafy.presentation.ui.user.JoinAgreeScreen
import com.ssafy.presentation.ui.user.JoinEmailScreen
import com.ssafy.presentation.ui.user.JoinNickNameScreen
import com.ssafy.presentation.ui.user.JoinPasswordScreen
import com.ssafy.presentation.ui.user.SignInScreen

@Composable
fun NavGraph(navController: NavHostController, window: Window) {
    NavHost(
        navController = navController,
        startDestination = SignInNav.SignIn.route
    ) {
        composable(route = SignInNav.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(route = SignInNav.Main.route) {
            MainScreen(window)
        }

        composable(route = SignInNav.FindPassword.route) {
            FindPasswordScreen(navController = navController)
        }

        composable(route = SignInNav.JoinPassword.route) {
            JoinPasswordScreen(navController = navController)
        }

        composable(route = SignInNav.JoinEmail.route) {
            JoinEmailScreen(navController = navController)
        }

        composable(route = SignInNav.JoinAgree.route) {
            JoinAgreeScreen(navController = navController)
        }

        composable(route = SignInNav.JoinNickName.route) {
            JoinNickNameScreen(navController = navController)
        }
    }
}