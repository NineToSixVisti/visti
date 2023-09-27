package com.ssafy.presentation.ui.story

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView


@Composable
fun StoryScreen(
    viewModel: WebViewViewModel = hiltViewModel(),
    pickImageLauncher: ActivityResultLauncher<String>,
    selectedImageUri: MutableState<Uri?>
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

                        webChromeClient = object : WebChromeClient() {
                            override fun onShowFileChooser(
                                webView: WebView,
                                filePathCallback: ValueCallback<Array<Uri>>,
                                fileChooserParams: FileChooserParams
                            ): Boolean {
                                try {
                                    pickImageLauncher.launch("image/*")
                                    selectedImageUri.value?.let {
                                        filePathCallback.onReceiveValue(arrayOf(it))
                                    } ?: run {
                                        filePathCallback.onReceiveValue(null)
                                    }
                                    selectedImageUri.value =
                                        null  // Reset the state after using the URI
                                } catch (e: ActivityNotFoundException) {
                                    filePathCallback.onReceiveValue(null)
                                    return false
                                }
                                return true
                            }
                        }

                        addJavascriptInterface(
                            object {
                                @JavascriptInterface
                                fun getToken(): String {
                                    return viewModel.accessToken.value.accessToken
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
