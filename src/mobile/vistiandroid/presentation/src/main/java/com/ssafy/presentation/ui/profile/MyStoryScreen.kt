package com.ssafy.presentation.ui.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView

private const val TAG = "MyStoryScreen"

@Composable
fun MyStoryScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    Log.d(TAG, "MyStoryScreen hilt viewModel id: ${viewModel.hashCode()}")
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            val webViewState = viewModel.webViewState

            WebView(
                state = webViewState,
                client = webViewClient,
                chromeClient = webChromeClient,
                onCreated = { webView ->
                    Log.d(TAG, "MyStoryScreen: ${webViewState.content.getCurrentUrl()}")

                    with(webView) {
                        settings.run {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            javaScriptCanOpenWindowsAutomatically = false
                        }
                    }
                }
            )
        }
    }
}

val webViewClient = AccompanistWebViewClient()
val webChromeClient = AccompanistWebChromeClient()