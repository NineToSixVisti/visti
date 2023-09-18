package com.ssafy.presentation.ui.user

import com.ssafy.domain.model.Story
import com.ssafy.domain.model.user.UserToken

data class UserState(
    val isLoading: Boolean = false,
    val token: UserToken = UserToken(),
    val error: String = ""
)