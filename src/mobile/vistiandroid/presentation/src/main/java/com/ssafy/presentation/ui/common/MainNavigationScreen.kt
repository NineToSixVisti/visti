package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.ui.home.HomeScreen
import com.ssafy.presentation.ui.like.LikeListScreen
import com.ssafy.presentation.ui.profile.ProfileScreen
import com.ssafy.presentation.ui.setting.InformationScreen
import com.ssafy.presentation.ui.setting.NotificationSettingScreen
import com.ssafy.presentation.ui.setting.SubscriptionScreen
import com.ssafy.presentation.ui.setting.UserAccountScreen
import com.ssafy.presentation.ui.story.StoryScreen

@Composable
fun MainNavigationScreen(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    ) {
    NavHost(
        modifier = Modifier,
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
            ProfileScreen(navController = navController)
        }

        settingsGraph(navController)
    }
}

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController
) {
    composable(route = SettingNav.Notification.route) {
        NotificationSettingScreen(navController = navController)
    }

    composable(route = SettingNav.Information.route) {
        InformationScreen(navController = navController)
    }

    composable(route = SettingNav.UserAccount.route) {
        UserAccountScreen(navController = navController)
    }

    composable(route = SettingNav.Subscription.route) {
        SubscriptionScreen(navController = navController)
    }
}

