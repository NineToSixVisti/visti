package com.ssafy.presentation.ui.common

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.presentation.LogInNav
import com.ssafy.presentation.MainScreen
import com.ssafy.presentation.ui.user.FindPasswordScreen
import com.ssafy.presentation.ui.user.JoinEmailScreen
import com.ssafy.presentation.ui.user.LogInScreen

@Composable
fun NavGraph(navController: NavHostController, window: Window) {
    NavHost(
        navController = navController,
        startDestination = LogInNav.LogIn.route
    ) {
        composable(route = LogInNav.LogIn.route) {
            LogInScreen(navController = navController)
        }

        composable(route = LogInNav.Main.route) {
            MainScreen(window)
        }

        composable(route = LogInNav.FindPassword.route) {
            FindPasswordScreen(navController = navController)
        }

        composable(route = LogInNav.JoinEmail.route) {
            JoinEmailScreen(navController = navController)
        }
    }
}