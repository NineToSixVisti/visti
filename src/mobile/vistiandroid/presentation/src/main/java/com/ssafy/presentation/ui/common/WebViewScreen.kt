package com.ssafy.presentation.ui.common

import android.webkit.JavascriptInterface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView


@Composable
fun WebViewScreen(
    id: String, mode: String,
    viewModel: WebViewViewModel = hiltViewModel()
) {
    //  Log.d(TAG, "MyStoryScreen hilt viewModel id: ${viewModel.hashCode()}")
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            val webViewState = viewModel.setWebViewState(id, mode)

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