package com.ssafy.presentation

import android.Manifest
import android.app.Activity
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.presentation.MainActivity.Companion.selectedImageUri
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavHost
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var selectedImageUri: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        NaverIdLoginSDK.initialize(
//            this@MainActivity,
//            "mE50MSQqCj6GYFbT2CVW",
//            "iytWOxmTy8",
//            "Visti"
//        )
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
                    MainScreen(this, galleryLauncher)
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
                this, arrayOf<String>(permissionId),
                REQUEST_PERMISSION
            )
        } else {

        }
    }

    // 파일 선택 결과 처리를 위한 ActivityResultLauncher
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    context: Context, pickImageLauncher: ActivityResultLauncher<Intent>
) {
    val mainNavController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            MainBottomNavigationBar(navController = mainNavController)
        },
    ) {
        MainNavHost(it, navController = mainNavController, context = context, pickImageLauncher)
    }
}

private const val REQUEST_PERMISSION = 1
