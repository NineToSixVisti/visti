package com.ssafy.presentation.ui.like

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.presentation.ui.common.StoryLazyVerticalGrid
import com.ssafy.presentation.ui.like.component.ToolbarWithLikeList
import com.ssafy.presentation.ui.theme.DarkBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikeListScreen(viewModel: ImageListViewModel = hiltViewModel()) {
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
                },
                modifier = Modifier.background(DarkBackgroundColor)
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val grouped = state.images.groupBy { it.author }
                    StoryLazyVerticalGrid(grouped)
                }
            }
        }
    }
}