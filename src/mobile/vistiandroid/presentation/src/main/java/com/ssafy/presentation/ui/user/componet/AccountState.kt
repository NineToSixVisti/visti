package com.ssafy.presentation.ui.user.componet

data class AccountState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)