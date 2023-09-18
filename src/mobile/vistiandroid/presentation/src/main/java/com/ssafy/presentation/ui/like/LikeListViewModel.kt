package com.ssafy.presentation.ui.like

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story
import com.ssafy.domain.repository.LikedStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeListViewModel @Inject constructor(
    private val repository: LikedStoryRepository
) : ViewModel() {

//    private val _likedStories = MutableStateFlow<PagingData<Story>>(PagingData.empty())
//    val likedStories: StateFlow<PagingData<Story>> = _likedStories
//
//    init {
//        getLikedStories(LikeSortType.DOWN)
//    }
//
//    fun getLikedStories(sortType: LikeSortType) {
//        viewModelScope.launch {
//            repository.getLikedStories(10, sortType).collect { pagingData ->
//                _likedStories.value = pagingData
//            }
//        }
//    }

    val likedStories: Flow<PagingData<Story>> = repository.getLikedStories(3, LikeSortType.DOWN).cachedIn(viewModelScope)
}