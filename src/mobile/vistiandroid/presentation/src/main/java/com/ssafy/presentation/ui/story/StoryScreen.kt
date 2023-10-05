package com.ssafy.presentation.ui.story

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.ssafy.presentation.MainActivity.Companion.selectedImageUri
import java.io.ByteArrayOutputStream


private const val TAG = "StoryScreen"

@Composable
fun StoryScreen(
    viewModel: WebViewViewModel = hiltViewModel(),
    pickImageLauncher: ActivityResultLauncher<Intent>
) {

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
                                    val intent = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )

                                    Log.d(TAG, "openGallery: open")
                                    // 결과 처리를 위한 ActivityResultLauncher를 호출하여 갤러리 화면을 엽니다.
                                    pickImageLauncher.launch(intent)
                                }

                                @JavascriptInterface
                                fun getSelectedImage(): String? {
                                    Log.d(TAG, "openGallery: $selectedImageUri")

                                    if (selectedImageUri != null) {
                                        val bitmap = selectedImageUri?.let {
                                            if (Build.VERSION.SDK_INT < 28) {
                                                MediaStore.Images.Media.getBitmap(
                                                    context.contentResolver,
                                                    it
                                                )
                                            } else {
                                                val source = ImageDecoder.createSource(
                                                    context.contentResolver,
                                                    it
                                                )
                                                ImageDecoder.decodeBitmap(source)
                                            }
                                        }

                                        if(bitmap != null) {
                                            val byteArray = bitmapToByteArray(bitmap)
                                            return "data:image/jpeg;base64,${Base64.encodeToString(byteArray, Base64.DEFAULT)}"
                                        }
                                    }

                                    return null
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

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

val webViewClient = AccompanistWebViewClient()
val webChromeClient = AccompanistWebChromeClient()