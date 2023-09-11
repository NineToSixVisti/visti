package com.ssafy.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.presentation.ui.home.HomeScreen
import com.ssafy.presentation.ui.like.LikeListScreen
import com.ssafy.presentation.ui.mypage.MyPageScreen
import com.ssafy.presentation.ui.story.StoryScreen
import com.ssafy.presentation.ui.theme.NavigationBackgroundColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import com.ssafy.presentation.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistiAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        it
        NavigationHost(navController = navController)
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainNav,
    navDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Icon(painter = painterResource(screen.icon), contentDescription = " NavBar Icon")
        },
        label = {
            Text(text = screen.title)
        },
        selected = navDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route)
        })
}

//@Composable
//fun BottomBar(navController: NavHostController) {
//
//    val screens = listOf(
//        MainNav.Home,
//        MainNav.Memory,
//        MainNav.Like,
//        MainNav.Profile
//    )
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    NavigationBar {
//        screens.forEach { screen ->
//            AddItem(
//                screen = screen,
//                navDestination = currentDestination,
//                navController = navController
//            )
//        }
//    }
//}
@Composable
fun BottomBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Memory,
        MainNav.Like,
        MainNav.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        tonalElevation = 0.dp,
        modifier = Modifier
            .padding(15.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(
                    40.dp
                )
                clip = true
                shadowElevation = 10f
            },
        containerColor = NavigationBackgroundColor
    ) {
//        NavigationBar {
//            bottomNavigationItems.forEach { screen ->
//                AddItem(
//                    screen = screen,
//                    navDestination = currentDestination,
//                    navController = navController
//                )
//            }
//        }
        bottomNavigationItems.forEach { item ->
            Log.e("NavigationBarItem",currentDestination?.route+item.route)
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SecondaryColor,
                    selectedTextColor = SecondaryColor,
                    unselectedIconColor = White,
                    unselectedTextColor = White,
                    indicatorColor = Color.Transparent
                ),
                label = { Text(text = item.title) },
                icon = { Icon(painter = painterResource(id = item.icon), item.route) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,

                onClick = {
                    navController.navigate(item.route)
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(
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