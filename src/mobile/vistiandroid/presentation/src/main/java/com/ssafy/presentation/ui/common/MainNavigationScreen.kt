package com.ssafy.presentation.ui.common

import android.view.Window
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.ui.home.HomeScreen
import com.ssafy.presentation.ui.like.LikeListScreen
import com.ssafy.presentation.ui.mypage.MyPageScreen
import com.ssafy.presentation.ui.story.StoryScreen

@Composable
fun MainNavigationScreen(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    window: Window
) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = MainNav.Home.route,
    ) {
        composable(MainNav.Home.route) {
            HomeScreen(window)
        }
        composable(MainNav.Memory.route) {
            StoryScreen()
        }
        composable(MainNav.Like.route) {
            LikeListScreen()
        }
        composable(MainNav.Profile.route) {
            MyPageScreen()
        }
    }
}