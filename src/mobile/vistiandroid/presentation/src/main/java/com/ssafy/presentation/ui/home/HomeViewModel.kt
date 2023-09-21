package com.ssafy.presentation.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryBoxUseCase
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryUseCase
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import com.ssafy.presentation.ui.like.MemberState
import com.ssafy.presentation.ui.like.MyStoryBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    private val getHomeStoryUseCase: GetHomeStoryUseCase,
    private val getHomeStoryBoxUseCase: GetHomeStoryBoxUseCase,
) : ViewModel() {

    private val _homeStoryState = mutableStateOf(HomeStoryState())
    val homeStoryState: State<HomeStoryState> = _homeStoryState

    private val _homeStoryBoxState = mutableStateOf(HomeStoryBoxState())
    val homeStoryBoxState: State<HomeStoryBoxState> = _homeStoryBoxState

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    private val _myStoryBoxes = mutableStateOf(MyStoryBoxState())
    val myStoryBoxes: State<MyStoryBoxState> = _myStoryBoxes

    init {
        getHomeStory()
        getHomeStoryBox()
        // getMemberInformation()
    }

    private fun getHomeStory() {
        getHomeStoryUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeStoryState.value = HomeStoryState(stories = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _homeStoryState.value =
                        HomeStoryState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _homeStoryState.value = HomeStoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getHomeStoryBox() {
        getHomeStoryBoxUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeStoryBoxState.value =
                        HomeStoryBoxState(storyBox = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _homeStoryBoxState.value =
                        HomeStoryBoxState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _homeStoryBoxState.value = HomeStoryBoxState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getMemberInformation() {
        getMemberInformUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memberInformation.value = MemberState(
                        memberInformation = result.data
                            ?: Member()
                    )
                }

                is Resource.Error -> {
                    _memberInformation.value =
                        MemberState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _memberInformation.value = MemberState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}