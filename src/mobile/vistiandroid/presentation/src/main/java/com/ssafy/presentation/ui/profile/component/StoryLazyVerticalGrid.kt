package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ssafy.domain.model.Story
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.StoryItem
import com.ssafy.presentation.ui.like.component.EmptyItemView
import com.ssafy.presentation.ui.like.component.ErrorItem
import com.ssafy.presentation.ui.like.component.LoadingView

@Composable
fun StoryLazyVerticalGrid(
    stories: LazyPagingItems<Story>,
    storyCount: String,
    navController: NavController,
) {
    when {
        stories.loadState.refresh is LoadState.Loading || stories.loadState.append is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        //TODO 이거 머죠? 위에꺼랑 조건이 똑같은데?
//        stories.loadState.refresh is LoadState.Loading || stories.loadState.append is LoadState.Loading -> {
//            val errorState = stories.loadState.refresh as LoadState.Error
//            val errorMessage = errorState.error.localizedMessage ?: "An unknown error occurred"
//
//            ErrorItem(
//                message = errorMessage,
//                modifier = Modifier.fillMaxWidth(),
//                onClickRetry = { stories.retry() }
//            )
//        }

        storyCount == "0" -> {
            EmptyItemView(
                modifier = Modifier.fillMaxSize(),
                stringResource(R.string.empty_story)
            )
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        this.items(stories.itemCount) { index ->
            val image = stories[index]
            if (image != null) {
                StoryItem(image, navController)
            }
        }
    }
}
