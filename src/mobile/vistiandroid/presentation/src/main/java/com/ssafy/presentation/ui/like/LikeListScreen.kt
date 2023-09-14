package com.ssafy.presentation.ui.like

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
import com.ssafy.presentation.ui.like.component.LikeLazyVerticalGrid
import com.ssafy.presentation.ui.like.component.ToolbarWithLikeList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LikeListScreen(viewModel: LikeListViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    when {
        state.error.isNotBlank() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error)
            }
        }

        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
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
                    val grouped = state.stories.groupBy { story ->
                        val createdAtDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(story.createdAt)
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