package com.ssafy.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.user.SignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUseCase: SignUseCase,
) : ViewModel() {

    private val _userToken = MutableStateFlow<UserState>(UserState())
    val userToken: StateFlow<UserState> = _userToken.asStateFlow()

    fun signIn(email: String, password: String) {
        signUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userToken.value = UserState(token = result.data)
                }

                is Resource.Error -> {
                    _userToken.value = UserState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _userToken.value = UserState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun delete() {
        _userToken.value = UserState()
    }
}