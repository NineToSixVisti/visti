package com.ssafy.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.theme.White
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(
    type: String? = null,
    articleId: String? = null
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(500.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_backgroud_sky),
                    contentDescription = "toolbar background",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(state.toolbarState.progress)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(1 - state.toolbarState.progress)
                        .background(Color.Red)
                )
            }
            Image(
                modifier = Modifier
                    .padding(0.dp, 16.dp, 0.dp, 16.dp)
                    .road(Alignment.Center, Alignment.BottomEnd)
                    .alpha(1 - state.toolbarState.progress),
                painter = painterResource(id = R.drawable.image_39),
                contentDescription = "home_logo"
            )
        },
    ) {
        Scaffold(
            bottomBar = {
                if (MainNav.isMainRoute(currentRoute)) {
                    MainBottomNavigationBar(
                        navController = navController,
                        currentRoute = currentRoute
                    )
                }
            },
        ) {
            MainNavigationScreen(
                innerPaddings = it,
                navController = navController
            )
        }

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            MainContent()
            MainContent()
            MainContent()
            MainContent()
        }


    }

}

@Composable
fun MainContent() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = R.raw.animation_lm9xow59)
    )
    LottieAnimation(
        modifier = Modifier.size(size = 240.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever // animate forever
    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Memory,
        MainNav.Like,
        MainNav.Profile,
    )

    NavigationBar(
        tonalElevation = 0.dp,
        modifier = Modifier.graphicsLayer {
            shape = RoundedCornerShape(
                16.dp
            )
            clip = true
            shadowElevation = 20f
        },
    ) {
        val isDarkTheme = isSystemInDarkTheme()
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SecondaryColor,
                    selectedTextColor = SecondaryColor,
                    unselectedIconColor = White,
                    unselectedTextColor = White,
                    indicatorColor = if (isDarkTheme) {
                        DarkBackgroundColor
                    } else {
                        LightBackgroundColor
                    }
                ),
                label = { Text(text = item.title) },
                icon = { Icon(painter = painterResource(id = item.icon), item.route) },
                selected = currentRoute == item.route,
                onClick = {

                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    innerPaddings: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = MainNav.Home.route
    ) {
        composable(
            route = MainNav.Home.route
        ) {

        }
        composable(
            route = MainNav.Memory.route
        ) {
//            MainMapScreen(navController)
        }
        composable(
            route = MainNav.Like.route
        ) {
//            CommunityScreen(navController)
        }
        composable(
            route = MainNav.Profile.route
        ) {
//            MainChattingScreen(navController)
        }
    }
}