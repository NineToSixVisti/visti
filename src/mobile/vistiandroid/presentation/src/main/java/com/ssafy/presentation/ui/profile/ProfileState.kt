package com.ssafy.presentation.ui.profile

import com.ssafy.domain.model.Member

data class ProfileState(
    val isLoading: Boolean = false,
    val memberInformation: Member = Member(),
    val error: String = ""
)