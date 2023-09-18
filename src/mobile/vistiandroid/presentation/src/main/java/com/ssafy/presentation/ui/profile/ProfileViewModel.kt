package com.ssafy.presentation.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.repository.MemberInformationRepository
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import com.ssafy.presentation.ui.like.MemberState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    private val repository: MemberInformationRepository,
) : ViewModel() {

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    private val _myStoryBoxes = MutableStateFlow<PagingData<StoryBox>>(PagingData.empty())
    val myStoryBoxes: StateFlow<PagingData<StoryBox>> = _myStoryBoxes

    private val _myStories = MutableStateFlow<PagingData<Story>>(PagingData.empty())
    val myStories: StateFlow<PagingData<Story>> = _myStories

    fun initializeData() {
        getMyStories()
        getMemberInformation()
        getMyStoryBoxes()
    }

    private fun getMyStories() {
        viewModelScope.launch {
            repository.getMyStories(3).collect { pagingData ->
                _myStories.value = pagingData
            }
        }
    }

    private fun getMyStoryBoxes() {
        viewModelScope.launch {
            repository.getMyStoryBoxes(10).collect { pagingData ->
                _myStoryBoxes.value = pagingData
            }
        }
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
