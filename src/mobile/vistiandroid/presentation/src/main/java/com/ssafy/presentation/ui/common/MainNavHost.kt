package com.ssafy.presentation.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.presentation.MainActivity.Companion.selectedImageUri
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.SignInNav
import com.ssafy.presentation.ui.home.HomeScreen
import com.ssafy.presentation.ui.like.LikeListScreen
import com.ssafy.presentation.ui.profile.ProfileScreen
import com.ssafy.presentation.ui.setting.InformationScreen
import com.ssafy.presentation.ui.setting.NotificationSettingScreen
import com.ssafy.presentation.ui.setting.SubscriptionScreen
import com.ssafy.presentation.ui.setting.UserAccountScreen
import com.ssafy.presentation.ui.story.StoryScreen
import com.ssafy.presentation.ui.user.FindPasswordScreen
import com.ssafy.presentation.ui.user.JoinAgreeScreen
import com.ssafy.presentation.ui.user.JoinEmailScreen
import com.ssafy.presentation.ui.user.JoinNickNameScreen
import com.ssafy.presentation.ui.user.JoinPasswordScreen
import com.ssafy.presentation.ui.user.SignInScreen

@Composable
fun MainNavHost(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    context: Context,
    pickImageLauncher: ActivityResultLauncher<Intent>
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = SignInNav.SignIn.route,
    ) {
        composable(MainNav.Home.route) {
            HomeScreen()
        }
        composable(MainNav.Memory.route) {
            StoryScreen(pickImageLauncher = pickImageLauncher)
        }
        composable(MainNav.Like.route) {
            LikeListScreen()
        }
        composable(MainNav.Profile.route) {
            ProfileScreen(navController = navController)
        }

        settingsGraph(navController, context = context)
        signupGraph(navController, context = context)
    }
}

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    context: Context
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

fun NavGraphBuilder.signupGraph(
    navController: NavHostController,
    context: Context
) {
    composable(route = SignInNav.SignIn.route) {
        SignInScreen(navController = navController, context)
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


