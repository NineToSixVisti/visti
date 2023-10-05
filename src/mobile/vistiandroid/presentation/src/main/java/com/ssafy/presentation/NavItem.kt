package com.ssafy.presentation

import androidx.annotation.DrawableRes
import com.ssafy.presentation.NavigationRouteName.FIND_PASSWORD
import com.ssafy.presentation.NavigationRouteName.JOIN_AGREE
import com.ssafy.presentation.NavigationRouteName.JOIN_EMAIL
import com.ssafy.presentation.NavigationRouteName.JOIN_NICKNAME
import com.ssafy.presentation.NavigationRouteName.JOIN_PASSWORD
import com.ssafy.presentation.NavigationRouteName.SIGN_IN
import com.ssafy.presentation.NavigationRouteName.MAIN
import com.ssafy.presentation.NavigationRouteName.MAIN_HOME
import com.ssafy.presentation.NavigationRouteName.MAIN_LIKE
import com.ssafy.presentation.NavigationRouteName.MAIN_MEMORY
import com.ssafy.presentation.NavigationRouteName.MAIN_PROFILE
import com.ssafy.presentation.NavigationRouteName.SETTING_INFORMATION
import com.ssafy.presentation.NavigationRouteName.SETTING_MY_STORY
import com.ssafy.presentation.NavigationRouteName.SETTING_NOTIFICATION
import com.ssafy.presentation.NavigationRouteName.SETTING_SUBSCRIPTION
import com.ssafy.presentation.NavigationRouteName.SETTING_USER_ACCOUNT

object NavigationRouteName {
    const val MAIN = "메인"
    const val MAIN_HOME = "홈"
    const val MAIN_MEMORY = "추억"
    const val MAIN_LIKE = "좋아요"
    const val MAIN_PROFILE = "프로필"
    const val SETTING_NOTIFICATION = "알림 설정"
    const val SETTING_MY_STORY = "내 스토리"
    const val SETTING_INFORMATION = "정보"
    const val SETTING_USER_ACCOUNT = "계정"
    const val SETTING_SUBSCRIPTION = "구독"

    const val SIGN_IN = "로그인"
    const val FIND_PASSWORD = "비밀번호 찾기"
    const val JOIN_EMAIL = "회원가입 이메일"
    const val JOIN_PASSWORD = "회원가입 비밀번호"
    const val JOIN_NICKNAME = "회원가입 닉네임"
    const val JOIN_AGREE = "회원가입 이용동의"
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
                MAIN_HOME, MAIN_LIKE, MAIN_PROFILE -> true
                else -> false
            }
        }
    }
}

sealed class SettingNav(
    override val route: String,
    override val title: String
) : Destination {
    object MyStory : SettingNav(SETTING_MY_STORY, SETTING_MY_STORY)
    object Notification : SettingNav(SETTING_NOTIFICATION, SETTING_NOTIFICATION)
    object Information : SettingNav(SETTING_INFORMATION, SETTING_INFORMATION)
    object UserAccount :
        SettingNav(SETTING_USER_ACCOUNT, SETTING_USER_ACCOUNT)

    object Subscription :
        SettingNav(SETTING_SUBSCRIPTION, SETTING_SUBSCRIPTION)
}

sealed class SignInNav(
    override val route: String,
    override val title: String
) : Destination {
    object SignIn : SignInNav(SIGN_IN, SIGN_IN)
    object Main : SignInNav(MAIN, MAIN)
    object FindPassword : SignInNav(FIND_PASSWORD, FIND_PASSWORD)
    object JoinEmail :
        SignInNav(JOIN_EMAIL, JOIN_EMAIL)

    object JoinAgree :
        SignInNav(JOIN_AGREE, JOIN_AGREE)

    object JoinPassword : SignInNav(JOIN_PASSWORD, JOIN_PASSWORD)
    object JoinNickName : SignInNav(JOIN_NICKNAME, JOIN_NICKNAME)
}
