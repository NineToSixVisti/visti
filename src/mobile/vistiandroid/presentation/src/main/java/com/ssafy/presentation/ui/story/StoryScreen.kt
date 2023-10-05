package com.ssafy.presentation.ui.story

import android.util.Log
import android.webkit.JavascriptInterface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.SignInNav

@Composable
fun StoryScreen(
    id: String,
    viewModel: WebViewViewModel = hiltViewModel()
) {
//    val accessTokenState = viewModel.accessToken.collectAsState()
//    Log.e("accessTokenState",accessTokenState.value.accessToken.toString())
//    if(accessTokenState.value.accessToken=="accessToken")
//    {
//        navController.navigate(route = SignInNav.SignIn.route) {
//            popUpTo(navController.graph.id) {
//                inclusive = true
//            }
//        }
//
//    }
//    else
//    {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                val webViewState = viewModel.setWebViewState(id)
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
  //  }

}

val webViewClient = AccompanistWebViewClient()
val webChromeClient = AccompanistWebChromeClient()