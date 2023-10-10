plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ssafy.presentation"
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Versions.MIN_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.AndroidX.COMPOSE)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.MATERIAL3)
    implementation("androidx.paging:paging-common-ktx:3.2.1")
    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.Test.TEST_EXT)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //coil
    implementation(Dependencies.COIL.COIL_ANDROID)
    implementation(Dependencies.COIL.COIL_COMPOSE)

    //navigation
    implementation(Dependencies.NAVIGATION)

    //constraintLayout
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)

    //viewmodel
    implementation(Dependencies.AndroidX.LIFECYCLE_VIEWMODEL_KTX)

    //livedata
    implementation(Dependencies.AndroidX.LIVEDATA_KTX)

    //Hilt
    implementation(Dependencies.Hilt.DAGGER_ANDROID)
    kapt(Dependencies.Hilt.DAGGER_COMPILER)
    implementation(Dependencies.Hilt.HILT_COMPOSE)

    //retrofit & okhttp
    implementation(Dependencies.RETROFIT.RETROFIT_CORE)
    implementation(Dependencies.RETROFIT.RETROFIT_CONVERTER)
    implementation(Dependencies.OKHTTP.OKHTTP_CORE)
    implementation(Dependencies.OKHTTP.OKHTTP_LOGGING)

    //toolbar
    implementation(Dependencies.TOOLBAR)

    //lottie
    implementation(Dependencies.LOTTIE)

    //시스템 화면 접근
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    //paging
    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-runtime:$pagingVersion")
    implementation("androidx.paging:paging-compose:3.2.1")

    //datastore
    implementation(Dependencies.DATASTORE)

    //카카오 로그인
    implementation(Dependencies.KAKAO)

    //네이버 로그인
    implementation(Dependencies.NAVER)

    // webview
    implementation("com.google.accompanist:accompanist-webview:0.24.13-rc")

    //fcm
    implementation(platform(Dependencies.FIREBASE.FIREBASE_ANDROID))
    implementation(Dependencies.FIREBASE.FIREBASE_MESSAGE)
}