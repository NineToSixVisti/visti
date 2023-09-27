package com.ssafy.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavHost
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import com.ssafy.presentation.ui.user.SignInScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)

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
                    MainScreen(this)
                }
            }
        }
    }
    private fun createNotificationChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    companion object {
        const val CHANNEL_ID = "visti_channel"
        const val CHANNEL_NAME = "visti"
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
        MainNavHost(it, navController = mainNavController, context = context)
    }
}


