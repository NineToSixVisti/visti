package com.ssafy.presentation.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.NavigationBackgroundColor
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.theme.White


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainBottomNavigationBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Memory,
        MainNav.Like,
        MainNav.Profile,
    )

    var visibleBottomBar by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibleBottomBar,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        NavigationBar(
            tonalElevation = 0.dp,
            modifier = Modifier
                .padding(15.dp)
                .navigationBarsPadding()
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        40.dp
                    )
                    clip = true
                    shadowElevation = 10f
                },
            containerColor = NavigationBackgroundColor
        ) {
            bottomNavigationItems.forEach { item ->
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
                    selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    alwaysShowLabel = false
                )
            }
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SecondaryColor,
                    selectedTextColor = SecondaryColor,
                    unselectedIconColor = White,
                    unselectedTextColor = White,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "navigation close"
                    )
                },
                onClick = {
                    visibleBottomBar = !visibleBottomBar
                },
                selected = false
            )// 종료 네비게이션 아이템
        }
    }
    AnimatedVisibility(
        visible = !visibleBottomBar,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            FloatingActionButton(
                containerColor = PrimaryColor,
                contentColor = White,
                onClick = { visibleBottomBar = !visibleBottomBar },
                modifier = Modifier
                    .padding(18.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_category_24),
                    contentDescription = "navigation",
                    modifier = Modifier.padding(25.dp)
                )
            }
        }
    }
//    if (visibleBottomBar) {
//        NavigationBar(
//            tonalElevation = 0.dp,
//            modifier = Modifier
//                .padding(15.dp)
//                .navigationBarsPadding()
//                .graphicsLayer {
//                    shape = RoundedCornerShape(
//                        40.dp
//                    )
//                    clip = true
//                    shadowElevation = 10f
//                },
//            containerColor = NavigationBackgroundColor
//        ) {
//            bottomNavigationItems.forEach { item ->
//                NavigationBarItem(
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = SecondaryColor,
//                        selectedTextColor = SecondaryColor,
//                        unselectedIconColor = White,
//                        unselectedTextColor = White,
//                        indicatorColor = Color.Transparent
//                    ),
//                    label = { Text(text = item.title) },
//                    icon = { Icon(painter = painterResource(id = item.icon), item.route) },
//                    selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == item.route } == true,
//                    onClick = {
//                        navController.navigate(item.route)
//                    },
//                    alwaysShowLabel = false
//                )
//            }
//            NavigationBarItem(
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = SecondaryColor,
//                    selectedTextColor = SecondaryColor,
//                    unselectedIconColor = White,
//                    unselectedTextColor = White,
//                    indicatorColor = Color.Transparent
//                ),
//                icon = {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "navigation close"
//                    )
//                },
//                onClick = {
//                    visibleBottomBar = !visibleBottomBar
//                },
//                selected = false
//            )// 종료 네비게이션 아이템
//        }
//    } else {
//        Column(
//            horizontalAlignment = Alignment.End,
//            verticalArrangement = Arrangement.Bottom,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            FloatingActionButton(
//                containerColor = PrimaryColor,
//                contentColor = White,
//                onClick = { visibleBottomBar = !visibleBottomBar },
//                modifier = Modifier
//                    .padding(15.dp)
//                    .navigationBarsPadding(),
//                shape = RoundedCornerShape(40.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_category_24),
//                    contentDescription = "navigation",
//                    modifier = Modifier.padding(25.dp)
//                )
//            }
//        }
//    }

}