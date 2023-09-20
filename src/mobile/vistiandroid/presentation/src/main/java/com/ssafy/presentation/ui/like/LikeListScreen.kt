package com.ssafy.presentation.ui.like

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.presentation.ui.common.StoryItem
import com.ssafy.presentation.ui.like.component.ErrorItem
import com.ssafy.presentation.ui.like.component.LoadingItem
import com.ssafy.presentation.ui.like.component.LoadingView
import com.ssafy.presentation.ui.like.component.ToolbarWithLikeList
import com.ssafy.presentation.ui.like.component.header
import kotlinx.coroutines.flow.retry
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LikeListScreen(viewModel: LikeListViewModel = hiltViewModel()) {
    val likedStories = viewModel.likedStories

    Scaffold(
        topBar = {
            ToolbarWithLikeList()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val lazyLikedStories = likedStories

            val grouped = lazyLikedStories.itemSnapshotList.groupBy { story ->
                val createdAtDate =
                    SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    ).parse(
                        story?.createdAt ?: LocalDateTime.now().toString()
                    )
                val formattedCreatedAt = if (createdAtDate != null) {
                    SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(createdAtDate)
                } else {
                    val currentDate = Date()
                    SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentDate)
                }
                formattedCreatedAt
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                grouped.forEach { (title, images) ->
                    header {
                        Text(
                            title,
                            modifier = Modifier.padding(
                                top = 16.dp,
                                bottom = 16.dp,
                                start = 16.dp,
                                end = 8.dp
                            )
                        )
                    }

                    this.items(images) { image ->
                        if (image != null) {
                            StoryItem(image)
                        }
                    }

                    lazyLikedStories.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingView(modifier = Modifier.fillMaxSize()) }
                            }

                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val e = lazyLikedStories.loadState.refresh as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        modifier = Modifier.fillMaxSize(),
                                        onClickRetry = { retry() }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                val e = lazyLikedStories.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}