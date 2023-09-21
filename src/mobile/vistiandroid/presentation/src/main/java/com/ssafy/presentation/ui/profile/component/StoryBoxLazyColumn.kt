package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.StoryBoxItem
import com.ssafy.presentation.ui.like.component.EmptyItemView
import com.ssafy.presentation.ui.like.component.ErrorItem
import com.ssafy.presentation.ui.like.component.LoadingView

@Composable
fun StoryBoxLazyColumn(boxes: LazyPagingItems<StoryBox>, storyBoxCount: String) {
    when {
        boxes.loadState.refresh is LoadState.Loading || boxes.loadState.append is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        boxes.loadState.refresh is LoadState.Error || boxes.loadState.append is LoadState.Error -> {
            val errorState = boxes.loadState.refresh as LoadState.Error
            val errorMessage = errorState.error.localizedMessage ?: "An unknown error occurred"

            ErrorItem(
                message = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                onClickRetry = { boxes.retry() }
            )
        }

        storyBoxCount == "0" -> {
            EmptyItemView(
                modifier = Modifier.fillMaxSize(),
                stringResource(R.string.empty_story_box)
            )
        }

        else -> {}
    }

    LazyColumn {
        items(boxes.itemCount) { index ->
            val item = boxes[index]
            if (item != null) {
                StoryBoxItem(item)
            }
        }
    }
}