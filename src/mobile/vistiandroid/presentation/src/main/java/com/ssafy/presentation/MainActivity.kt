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
import androidx.navigation.compose.rememberNavController
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavigationScreen
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import com.ssafy.presentation.ui.user.JoinAgreeScreen
import com.ssafy.presentation.ui.user.LogInScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VistiAndroidTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    JoinAgreeScreen()
//                    MainScreen(window)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(window: Window) {
    val navController = rememberNavController()
    WindowCompat.setDecorFitsSystemWindows(window, false)
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            MainBottomNavigationBar(navController = navController)
        },
    ) {
        MainNavigationScreen(it, navController = navController, window)
    }
}




