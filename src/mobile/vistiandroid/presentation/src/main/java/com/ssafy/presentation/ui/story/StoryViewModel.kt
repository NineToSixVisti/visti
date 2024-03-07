package com.ssafy.presentation.ui.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewState
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.story.StoryUseCase
import com.ssafy.domain.usecase.storybox.StoryBoxUseCase
import com.ssafy.presentation.ui.common.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
    private val storyBoxUseCase: StoryBoxUseCase,
) : ViewModel() {
    private val _accessToken = MutableStateFlow(StoryState())
    val accessToken: StateFlow<StoryState> = _accessToken.asStateFlow()

    init {
        getToken()
    }

    private fun enterStoryBox(storyBoxId: String) {
        viewModelScope.launch {
            storyBoxUseCase.enterStoryBox(storyBoxId)
        }
    }


    private fun getToken() {
        storyUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _accessToken.value = StoryState(accessToken = result.data!!)
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

    fun setWebViewState(id: String): WebViewState {
        if (id.isNotBlank()) {
            enterStoryBox(id)
            return WebViewState(
                WebContent.Url(
                    url = "${Network.vistiStoryUrl}storybox/detail/$id",
                    additionalHttpHeaders = emptyMap()
                )
            )
        }
        return WebViewState(
            WebContent.Url(
                url = "${Network.vistiStoryUrl}storybox",
                additionalHttpHeaders = emptyMap()
            )
        )
    }
}