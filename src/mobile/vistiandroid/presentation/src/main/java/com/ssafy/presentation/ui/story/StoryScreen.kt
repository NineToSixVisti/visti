package com.ssafy.presentation.ui.story

import android.webkit.JavascriptInterface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.ssafy.domain.repository.PreferenceDataSource

@Composable
fun StoryScreen(viewModel: WebViewViewModel = hiltViewModel(), preferenceDataSource: PreferenceDataSource = hiltViewModel()) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            val webViewState = viewModel.webViewState
            val webViewNavigator = viewModel.webViewNavigator
            val token = preferenceDataSource.getString("") ?: "access_token"

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
                        addJavascriptInterface(object {
                            @JavascriptInterface
                            fun getToken(): String {
                                return token
                            }
                        },
                            "access_token" )
                    }
                }
            )
        }
    }
}

val webViewClient = AccompanistWebViewClient()
val webChromeClient = AccompanistWebChromeClient()

class WebViewViewModel : ViewModel() {
    val webViewState = WebViewState(
        WebContent.Url(
            url = "http://j9d102.p.ssafy.io:3000/storybox",
            additionalHttpHeaders = emptyMap()
        )
    )
    val webViewNavigator = WebViewNavigator(viewModelScope)
}