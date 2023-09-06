object Versions {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 24
    const val TARGET_SDK_VERSION = 33
    const val APP_VERSION_CODE = 1
    const val APP_VERSION_NAME = "1.0"

    const val ANDROID_GRADLE_PLUGIN = "7.0.4"
    const val KOTLIN_VERSION = "1.6.10"
    const val HILT_VERSION = "1.0.0"
    const val DAGGER_VERSION = "2.45"
    const val LIFECYCLE_VERSION = "2.6.1"
    const val RETROFIT_VERSION = "2.9.0"
    const val OKHTTP_VERSION = "4.9.0"
    const val COMPOSE_VERSION = "1.7.2"
    const val COIL_VERSION = "2.2.2"
}

object Dependencies {

    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:1.9.0"
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.6.1"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        const val LIFECYCLE_RUNTIME_KTX =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_VERSION}"
        const val LIVEDATA_KTX =
            "androidx.compose.runtime:runtime-livedata:1.5.0"
        const val LIFECYCLE_VIEWMODEL_KTX =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE_VERSION}"
        const val COMPOSE = "androidx.activity:activity-compose:1.7.2"
    }

    object Hilt {
        const val DAGGER_ANDROID = "com.google.dagger:hilt-android:${Versions.DAGGER_VERSION}"
        const val DAGGER_COMPILER =
            "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_VERSION}"
        const val HILT_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_VERSION}"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val TEST_EXT = "androidx.test.ext:junit:1.1.5"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.1"
    }

    object RETROFIT {
        const val RETROFIT_CONVERTER =
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
        const val RETROFIT_CORE = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
    }

    object OKHTTP {
        const val OKHTTP_LOGGING =
            "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}"
        const val OKHTTP_CORE = "com.squareup.okhttp3:okhttp:${Versions.RETROFIT_VERSION}"
    }

    object COIL {
        const val COIL_ANDROID = "io.coil-kt:coil:${Versions.COIL_VERSION}"
        const val COIL_COMPOSE = "io.coil-kt:coil-compose:${Versions.COIL_VERSION}"
    }

    const val TIMBER = "com.jakewharton.timber:timber:5.0.1"

    const val GSON = "com.google.code.gson:gson:2.8.8"

    const val MATERIAL = "com.google.android.material:material:1.9.0"

    const val NAVIGATION = "androidx.navigation:navigation-compose:2.5.3"
}