package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ssafy.domain.model.StoryBox
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.StoryBoxItem

@Composable
fun StoryBoxLazyColumn(boxes: LazyPagingItems<StoryBox>) {
    when (boxes.loadState.refresh) {
        LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.log_in_logo_description))
            }
        }
        else -> {
            LazyColumn {
                items(boxes.itemCount) {index ->
                    val item = boxes[index]
                    if (item != null) {
                        StoryBoxItem(item)
                    }
                }
            }
        }
    }
}