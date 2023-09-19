package com.ssafy.presentation.ui.like

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story
import com.ssafy.domain.repository.LikedStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LikeListViewModel @Inject constructor(
    repository: LikedStoryRepository
) : ViewModel() {

    val likedStories: Flow<PagingData<Story>> =
        repository.getLikedStories(LikeSortType.DOWN)
}

