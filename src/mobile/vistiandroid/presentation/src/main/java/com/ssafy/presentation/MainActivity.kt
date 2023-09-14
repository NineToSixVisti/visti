package com.ssafy.presentation

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavigationScreen
import com.ssafy.presentation.ui.common.NavGraph
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            VistiAndroidTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController = navController, window)
//                    MainScreen(window)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(window: Window) {
    val mainNavController = rememberNavController()
    WindowCompat.setDecorFitsSystemWindows(window, false)
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            MainBottomNavigationBar(navController = mainNavController)
        },
    ) {
        MainNavigationScreen(it, navController = mainNavController, window)
    }
}




