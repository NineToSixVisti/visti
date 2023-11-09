package com.ssafy.presentation.ui.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewState
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.MemberInformationRepository
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    repository: MemberInformationRepository,
) : ViewModel() {

    private var myStoryLink = "https://visti-story.com/storydetail/"

    private val _memberInformation = mutableStateOf(ProfileState())
    val memberInformation: State<ProfileState> = _memberInformation

    val myStoryBoxes = repository.getMyStoryBoxes()
    val myStories = repository.getMyStories()

    fun initializeData() {
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

    var webViewState = WebViewState(
        WebContent.Url(
            url = "",
            additionalHttpHeaders = emptyMap()
        )
    )

    fun setWebViewState(id: String) {
        Log.e("setWebView", id)
        webViewState =  WebViewState(
            WebContent.Url(
                url = "$myStoryLink$id",
                additionalHttpHeaders = emptyMap()
            )
        )
    }
}
