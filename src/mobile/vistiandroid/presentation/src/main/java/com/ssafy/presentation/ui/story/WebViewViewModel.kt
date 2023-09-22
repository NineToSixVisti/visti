package com.ssafy.presentation.ui.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _accessToken = MutableStateFlow<StoryState>(StoryState())
    val accessToken: StateFlow<StoryState> = _accessToken.asStateFlow()

    init {
        getToken()
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

    val webViewState = WebViewState(
        WebContent.Url(
            url = "http://j9d102.p.ssafy.io:3000/storybox",
            additionalHttpHeaders = emptyMap()

        )
    )
    val webViewNavigator = WebViewNavigator(viewModelScope)
}