package com.ssafy.presentation.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryBoxList
import com.ssafy.domain.usecase.GetMemberInformUseCase
import com.ssafy.domain.usecase.GetMyStoryBoxUseCase
import com.ssafy.domain.usecase.GetMyStoryUseCase
import com.ssafy.presentation.ui.like.LikeListState
import com.ssafy.presentation.ui.like.MemberState
import com.ssafy.presentation.ui.like.MyStoryBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    private val getMyStoryUseCase: GetMyStoryUseCase,
    private val getMyStoryBoxUseCase: GetMyStoryBoxUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LikeListState())
    val state: State<LikeListState> = _state

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    private val _myStoryBoxes = mutableStateOf(MyStoryBoxState())
    val myStoryBoxes: State<MyStoryBoxState> = _myStoryBoxes

    init {
        getImages()
        getMemberInformation()
        getMyStoryBoxes()
    }

    private fun getImages() {
        getMyStoryUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LikeListState(stories = result.data?.content ?: emptyList())
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
                    _memberInformation.value = MemberState(memberInformation = result.data
                        ?: Member())
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


    private fun getMyStoryBoxes() {
        getMyStoryBoxUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _myStoryBoxes.value = MyStoryBoxState(boxes = result.data?: StoryBoxList())
                }

                is Resource.Error -> {
                    _myStoryBoxes.value = MyStoryBoxState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _myStoryBoxes.value = MyStoryBoxState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}