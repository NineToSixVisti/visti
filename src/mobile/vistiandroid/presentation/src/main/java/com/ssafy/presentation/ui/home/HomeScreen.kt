package com.ssafy.presentation.ui.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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

    /*CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp

            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .height(150.dp)
                    .pin()
            )

            Text(
                text = "Title",
                modifier = Modifier
                    .road(Alignment.CenterStart, Alignment.BottomEnd)
                    .padding(60.dp, 16.dp, 16.dp, 16.dp),
                color = Color.White,
                fontSize = textSize
            )

            Image(
                modifier = Modifier
                    .pin()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(100) {
                Text(
                    text = "Item $it",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .background(Color.Blue)
                .height(40.dp)
        )
    }*/
    Scaffold(

        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(navController = navController, currentRoute = currentRoute)
            }
        },
        topBar = {

        },
    ) {
        MainNavigationScreen(
            innerPaddings = it,
            navController = navController
        )
    }
    Box {
        mainContent()
    }
}

@Composable
fun mainContent() {
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