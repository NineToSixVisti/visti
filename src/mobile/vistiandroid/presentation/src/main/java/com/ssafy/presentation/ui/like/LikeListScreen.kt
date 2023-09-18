package com.ssafy.presentation.ui.like

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.ssafy.presentation.ui.like.component.LikeLazyVerticalGrid
import com.ssafy.presentation.ui.like.component.ToolbarWithLikeList
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LikeListScreen(viewModel: LikeListViewModel = hiltViewModel()) {
    val likedStories = viewModel.likedStories.collectAsLazyPagingItems()
    val likedStoryAppendState = likedStories.loadState.append

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
            
            when {
                likedStoryAppendState is LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = likedStoryAppendState.error.toString())
                    }
                }

                likedStoryAppendState is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    val grouped = likedStories.itemSnapshotList.groupBy { story ->
                        val createdAtDate =
                            SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault()
                            ).parse(
                                story?.createdAt ?: LocalDateTime.now().toString()
                            )
                        val formattedCreatedAt = if (createdAtDate != null) {
                            SimpleDateFormat("yyyy년 MM월", Locale.getDefault()).format(createdAtDate)
                        } else {
                            val currentDate = Date()
                            SimpleDateFormat("yyyy년 MM월", Locale.getDefault()).format(currentDate)
                        }
                        formattedCreatedAt
                    }
                    LikeLazyVerticalGrid(grouped)
                }
            }
        }
    }
}