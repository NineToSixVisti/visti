package com.ssafy.presentation.ui.story

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView


@Composable
fun StoryScreen(
    viewModel: WebViewViewModel = hiltViewModel(),
    pickImageLauncher: ActivityResultLauncher<Intent>,
    selectedImageUri: Uri?
) {

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            val webViewState = viewModel.webViewState
            val webViewNavigator = viewModel.webViewNavigator

            WebView(
                state = webViewState,
                client = webViewClient,
                chromeClient = webChromeClient,
                onCreated = { webView ->
                    with(webView) {
                        settings.run {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            javaScriptCanOpenWindowsAutomatically = false
                            allowFileAccess = true
                            allowContentAccess = true
                            loadWithOverviewMode = true
                        }

                        addJavascriptInterface(
                            object {
                                @JavascriptInterface
                                fun getToken(): String {
                                    return viewModel.accessToken.value.accessToken
                                }

                                @JavascriptInterface
                                fun openGallery() {
                                    // 갤러리 화면을 열기 위한 Intent를 생성
                                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                                    // 결과 처리를 위한 ActivityResultLauncher를 호출하여 갤러리 화면을 엽니다.
                                    pickImageLauncher.launch(intent)
                                }
                            },
                            "Android"
                        )
                    }
                }
            )
        }
    }
}

val webViewClient = AccompanistWebViewClient()
val webChromeClient = AccompanistWebChromeClient()