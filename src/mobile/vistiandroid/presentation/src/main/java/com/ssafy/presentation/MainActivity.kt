package com.ssafy.presentation


import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

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

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
                    } else {
                        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                    val mainNavController = rememberNavController()
                    val mainState = mainViewModel.accessToken.collectAsState()
                    when {
                        mainState.value.accessToken.isBlank() || mainState.value.accessToken == "accessToken" -> {
                            MainScreen(
                                mainNavController,
                                this,
                                SignInNav.SignIn.route,
                                galleryLauncher
                            )
                        }

                        mainState.value.accessToken.isNotBlank() -> {
                            MainScreen(mainNavController, this, MainNav.Home.route, galleryLauncher)
                        }
                    }

                }
            }
        }
    }


    private fun checkPermission(permissionId: String) {
        if (ContextCompat.checkSelfPermission(this, permissionId)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 허용되지 않은 경우 권한 요청
            ActivityCompat.requestPermissions(
                this, arrayOf(permissionId),
                REQUEST_PERMISSION
            )
        }
    }

    // 파일 선택 결과 처리를 위한 ActivityResultLauncher
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data
            }
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        var selectedImageUri: Uri? = null
        const val CHANNEL_ID = "visti_channel"
        const val CHANNEL_NAME = "visti"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainNavController: NavHostController,
    context: Context,
    route: String,
    pickImageLauncher: ActivityResultLauncher<Intent>,
) {

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
        Box(modifier = Modifier.padding(it))
        MainNavHost(
            navController = mainNavController,
            context = context,
            route,
            pickImageLauncher
        )
    }
}

private const val REQUEST_PERMISSION = 1
