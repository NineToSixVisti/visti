package com.ssafy.presentation.ui.user

import com.ssafy.domain.model.user.UserToken

data class TokenState(
    val isLoading: Boolean = false,
    val token: UserToken? = UserToken(),
    var error: String = ""
)