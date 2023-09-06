package com.ssafy.presentation

sealed class MainFab(val route: String, val icon: Int, val title: String) {
    object HOME : MainFab(NavigationRouteName.MAIN_HOME, R.drawable.ic_home, FabTitle.HOME)
    object MEMORY : MainFab(NavigationRouteName.MAIN_MEMORY, R.drawable.ic_memory, FabTitle.MEMORY)
    object LIKE : MainFab(NavigationRouteName.MAIN_LIKE, R.drawable.ic_like, FabTitle.LIKE)
    object PROFILE : MainFab(NavigationRouteName.MAIN_PROFILE, R.drawable.ic_profile, FabTitle.PROFILE)
}

object FabTitle {
    const val HOME = "홈"
    const val MEMORY = "추억"
    const val LIKE = "좋아요"
    const val PROFILE = "프로필"
}