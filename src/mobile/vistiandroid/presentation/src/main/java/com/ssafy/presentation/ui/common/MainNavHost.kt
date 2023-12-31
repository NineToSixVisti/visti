package com.ssafy.presentation.ui.common

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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
    route: String,
    pickImageLauncher: ActivityResultLauncher<Intent>

) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = route,
    ) {

        composable(MainNav.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            MainNav.Memory.route, deepLinks = listOf(navDeepLink {
                uriPattern = "visti://deeplink/{id}"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            Log.e("entry", id.toString())
            StoryScreen(id.toString(), pickImageLauncher)
        }
//        composable(MainNav.Memory.route) {
//            StoryScreen()
//        }
        composable(MainNav.Like.route) {
            LikeListScreen(navController = navController)
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
    composable(route = "${SettingNav.WebView.route}/{id}/{mode}",
        arguments = listOf(navArgument("id") {
            type = NavType.StringType
            defaultValue = ""
        },
            navArgument("mode") {
                type = NavType.StringType
                defaultValue = ""
            }

        )) { entry ->
        val id = entry.arguments?.getString("id")
        Log.e("entry", id.toString())
        val mode = entry.arguments?.getString("mode")
        Log.e("entry", mode.toString())
        WebViewScreen(id.toString(), mode.toString())
    }

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
        SignInScreen(navController, context)
    }
//    composable(route = SignInNav.Main.route) {
//        MainScreen(mainNavController, context)
//    }

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


