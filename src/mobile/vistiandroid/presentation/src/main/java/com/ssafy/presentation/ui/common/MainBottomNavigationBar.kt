package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.ui.theme.NavigationBackgroundColor
import com.ssafy.presentation.ui.theme.SecondaryColor
import com.ssafy.presentation.ui.theme.White


@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
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
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route)
                },
                alwaysShowLabel = false
            )
        }
    }
}