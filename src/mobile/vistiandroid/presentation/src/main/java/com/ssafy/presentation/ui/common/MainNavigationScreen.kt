package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = MainNav.Home.route,
    ) {
        composable(MainNav.Home.route) {
            HomeScreen()
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