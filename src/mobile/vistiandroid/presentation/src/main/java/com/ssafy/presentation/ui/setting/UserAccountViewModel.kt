package com.ssafy.presentation.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.account.AccountUseCase
import com.ssafy.domain.usecase.account.DeleteUserUseCase
import com.ssafy.presentation.ui.story.StoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserAccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _accessToken = MutableStateFlow<StoryState>(StoryState())
    val accessToken: StateFlow<StoryState> = _accessToken.asStateFlow()

    private val _deleteUser = MutableStateFlow<StoryState>(StoryState())
    val deleteUser: StateFlow<StoryState> = _deleteUser.asStateFlow()

    fun removeToken() {
        accountUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _accessToken.value = StoryState(accessToken = "")
                }

                is Resource.Error -> {
                    _accessToken.value = StoryState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _accessToken.value = StoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteUser() {
        deleteUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _deleteUser.value = StoryState(accessToken = result.data ?: "")
                }

                is Resource.Error -> {
                    _deleteUser.value = StoryState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _deleteUser.value = StoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}