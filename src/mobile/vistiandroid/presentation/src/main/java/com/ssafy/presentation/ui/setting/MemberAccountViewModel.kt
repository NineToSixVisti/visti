package com.ssafy.presentation.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.account.DeleteTokenUseCase
import com.ssafy.domain.usecase.account.DeleteUserUseCase
import com.ssafy.presentation.ui.user.componet.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MemberAccountViewModel @Inject constructor(
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val deleteMemberUseCase: DeleteUserUseCase,
) : ViewModel() {

    private val _accountState = MutableStateFlow(AccountState())
    val accountState: StateFlow<AccountState> = _accountState.asStateFlow()

    fun removeToken() {
        deleteTokenUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _accountState.value = AccountState(message = "로그아웃 성공")
                }

                is Resource.Error -> {
                    _accountState.value = AccountState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _accountState.value = AccountState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteMember() {
        deleteMemberUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _accountState.value = AccountState(message = "회원탈퇴 성공")
                }

                is Resource.Error -> {
                    _accountState.value = AccountState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _accountState.value = AccountState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}