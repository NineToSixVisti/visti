package com.ssafy.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavHost
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NaverIdLoginSDK.initialize(
            this@MainActivity,
            "mE50MSQqCj6GYFbT2CVW",
            "iytWOxmTy8",
            "Visti"
        )
        setContent {
            VistiAndroidTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    val mainNavController = rememberNavController()
                    val mainState = mainViewModel.accessToken.collectAsState()
                    when {
//                        mainState.value.isLoading -> {
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator()
//                            }
//                        }

                        mainState.value.accessToken.isBlank() -> {
                            MainScreen(mainNavController, this, SignInNav.SignIn.route)
                        }

                        mainState.value.accessToken.isNotBlank() -> {
                            MainScreen(mainNavController, this, MainNav.Home.route)
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainNavController: NavHostController, context: Context, route: String) {

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(
                    navController = mainNavController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        },
    ) {
        MainNavHost(it, navController = mainNavController, context = context, route)
    }
}


