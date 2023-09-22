package com.ssafy.presentation.ui.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import com.ssafy.domain.usecase.user.SignUseCase
import com.ssafy.domain.usecase.user.SocialUseCase
import com.ssafy.presentation.ui.profile.ProfileState
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
    private val socialUseCase: SocialUseCase,
    private val getMemberInformUseCase: GetMemberInformUseCase
) : ViewModel() {

    private val _userToken = MutableStateFlow<TokenState>(TokenState())
    val userToken: StateFlow<TokenState> = _userToken.asStateFlow()

    private val _memberInformation = mutableStateOf(ProfileState())
    val memberInformation: State<ProfileState> = _memberInformation

    init {
        getMemberInformation()
    }

    private fun getMemberInformation() {
        getMemberInformUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memberInformation.value = ProfileState(
                        memberInformation = result.data
                            ?: Member()
                    )
                }

                is Resource.Error -> {
                    _memberInformation.value =
                        ProfileState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _memberInformation.value = ProfileState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun signIn(email: String, password: String) {
        signUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userToken.value = TokenState(token = result.data)
                }

                is Resource.Error -> {
                    _userToken.value = TokenState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _userToken.value = TokenState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }
    fun socialSignIn(provider: String, accessToken: String) {
        socialUseCase(provider, accessToken).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userToken.value = TokenState(token = result.data)
                }

                is Resource.Error -> {
                    _userToken.value = TokenState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _userToken.value = TokenState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }


    fun delete() {
        _userToken.value = TokenState()
    }
}