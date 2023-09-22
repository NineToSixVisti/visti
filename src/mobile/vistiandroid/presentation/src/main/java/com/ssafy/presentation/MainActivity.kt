package com.ssafy.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavigationScreen
import com.ssafy.presentation.ui.common.NavGraph
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
            val navController = rememberNavController()
            VistiAndroidTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    val memberInformationState = mainViewModel.memberInformation.value

                    when {
                        memberInformationState.error.isNotBlank() -> {
                            NavGraph(navController = navController, this@MainActivity)
                        }

                        memberInformationState.memberInformation.nickname.isNotBlank() -> {
                            MainScreen(this)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    val mainNavController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            MainBottomNavigationBar(navController = mainNavController)
        },
    ) {
        MainNavigationScreen(it, navController = mainNavController, context = context)
    }
}


