package com.ssafy.presentation.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.MemberSimple
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.GetImagesUseCase
import com.ssafy.domain.usecase.GetMemberInformUseCase
import com.ssafy.presentation.ui.like.LikeListState
import com.ssafy.presentation.ui.like.MemberState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getMemberInformUseCase: GetMemberInformUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LikeListState())
    val state: State<LikeListState> = _state

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    init {
        getImages()
        getMemberInformation()
    }

    private fun getImages() {
        getImagesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LikeListState(stories = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = LikeListState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _state.value = LikeListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getMemberInformation() {
        getMemberInformUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memberInformation.value = MemberState(memberSimpleInformation = result.data ?: Member())
                }

                is Resource.Error -> {
                    _memberInformation.value = MemberState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _memberInformation.value = MemberState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}