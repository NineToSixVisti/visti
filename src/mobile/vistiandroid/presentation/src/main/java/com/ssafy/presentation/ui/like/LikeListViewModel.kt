package com.ssafy.presentation.ui.like

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Resource
import com.ssafy.domain.usecase.GetMyStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImagesUseCase: GetMyStoryUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LikeListState())
    val state: State<LikeListState> = _state

    init {
        getImages()
    }

    private fun getImages() {
        getImagesUseCase().onEach { result ->
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
}