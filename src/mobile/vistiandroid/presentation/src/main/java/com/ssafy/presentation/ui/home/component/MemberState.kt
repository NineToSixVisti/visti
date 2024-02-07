package com.ssafy.presentation.ui.home.component

import com.ssafy.domain.model.Member

data class MemberState(
    val isLoading: Boolean = false,
    val memberInformation: Member = Member(),
    val error: String = "",
)
