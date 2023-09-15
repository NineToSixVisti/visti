package com.ssafy.presentation

import androidx.annotation.DrawableRes
import com.ssafy.presentation.NavigationRouteName.MAIN_HOME
import com.ssafy.presentation.NavigationRouteName.MAIN_LIKE
import com.ssafy.presentation.NavigationRouteName.MAIN_MEMORY
import com.ssafy.presentation.NavigationRouteName.MAIN_PROFILE
import com.ssafy.presentation.NavigationRouteName.SETTING_INFORMATION
import com.ssafy.presentation.NavigationRouteName.SETTING_NOTIFICATION
import com.ssafy.presentation.NavigationRouteName.SETTING_SUBSCRIPTION
import com.ssafy.presentation.NavigationRouteName.SETTING_USER_ACCOUNT

object NavigationRouteName {
    const val MAIN_HOME = "홈"
    const val MAIN_MEMORY = "추억"
    const val MAIN_LIKE = "좋아요"
    const val MAIN_PROFILE = "프로필"
    const val SETTING_NOTIFICATION = "알림 설정"
    const val SETTING_INFORMATION = "정보"
    const val SETTING_USER_ACCOUNT = "계정"
    const val SETTING_SUBSCRIPTION = "구독"
}

interface Destination {
    val route: String
    val title: String
}

sealed class MainNav(
    override val route: String,
    @DrawableRes val icon: Int,
    override val title: String
) : Destination {
    object Home : MainNav(MAIN_HOME, R.drawable.ic_home, MAIN_HOME)
    object Memory : MainNav(MAIN_MEMORY, R.drawable.ic_memory, MAIN_MEMORY)
    object Like :
        MainNav(MAIN_LIKE, R.drawable.ic_like, MAIN_LIKE)

    object Profile : MainNav(MAIN_PROFILE, R.drawable.ic_profile, MAIN_PROFILE)

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                MAIN_HOME, MAIN_MEMORY, MAIN_LIKE, MAIN_PROFILE -> true
                else -> false
            }
        }
    }
}

sealed class SettingNav(
    override val route: String,
    override val title: String
) : Destination {
    object Notification : SettingNav(SETTING_NOTIFICATION, SETTING_NOTIFICATION)
    object Information : SettingNav(SETTING_INFORMATION, SETTING_INFORMATION)
    object UserAccount :
        SettingNav(SETTING_USER_ACCOUNT, SETTING_USER_ACCOUNT)
    object Subscription :
        SettingNav(SETTING_SUBSCRIPTION, SETTING_SUBSCRIPTION)
}