package com.ssafy.presentation.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewState
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.story.StoryUseCase
import com.ssafy.presentation.ui.story.StoryState
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

    val URL = "https://j9d102.p.ssafy.io"

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

    fun setWebViewState(id: String, mode: String): WebViewState {
        Log.e("setWebViewState","$id,$mode")
        return when (mode) {
            "storyBox" -> WebViewState(
                WebContent.Url(
                    url = "$URL/storybox/detail/$id",
                    additionalHttpHeaders = emptyMap()
                )
            )

            "story" -> WebViewState(
                WebContent.Url(
                    url = "$URL/storydetail/$id",
                    additionalHttpHeaders = emptyMap()
                )
            )

            else -> WebViewState(
                WebContent.Url(
                    url = "$URL/storybox",
                    additionalHttpHeaders = emptyMap()
                )
            )
        }
    }
}