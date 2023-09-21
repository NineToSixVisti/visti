package com.ssafy.visti.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "7616b7dee7a1483c6964ee8fce074beb")
    }
}