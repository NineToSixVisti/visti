plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ssafy.domain"
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
}

dependencies {
    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.MATERIAL)
    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.Test.TEST_EXT)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)

    //hilt
    implementation(Dependencies.Hilt.DAGGER_ANDROID)
    kapt(Dependencies.Hilt.DAGGER_COMPILER)
    implementation(Dependencies.Hilt.HILT_COMPOSE)
}