package com.ssafy.presentation.ui.like

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.Story
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.StoryItem
import com.ssafy.presentation.ui.like.component.EmptyItemView
import com.ssafy.presentation.ui.like.component.ErrorItem
import com.ssafy.presentation.ui.like.component.GridGroup
import com.ssafy.presentation.ui.like.component.LoadingView
import com.ssafy.presentation.ui.like.component.SortTypeRadioGroup
import com.ssafy.presentation.ui.like.component.header
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LikeListScreen(navController: NavController) {
    var selectedSortOption by remember {
        mutableStateOf(LikeSortType.DOWN)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    SortTypeRadioGroup(selectedSortOption) { newSortType ->
                        selectedSortOption = newSortType
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (selectedSortOption) {
                LikeSortType.DOWN -> {
                    DisplayLikedStoriesByDescend(navController = navController)
                }

                LikeSortType.UP -> {
                    DisplayLikedStoriesByAscend(navController = navController)
                }

                LikeSortType.RANDOM -> {
                    DisplayLikedStoriesByRandom(navController = navController)
                }
            }
        }
    }
}

@Composable
fun DisplayLikedStoriesByDescend(
    viewModel: LikeListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val likedStories = viewModel.likedStoriesByDescend
    val lazyLikedStories = likedStories.collectAsLazyPagingItems()

    val gridGroups = remember { mutableStateListOf<GridGroup>() }

    LaunchedEffect(lazyLikedStories.itemSnapshotList) {
        updateGridGroups(lazyLikedStories, gridGroups)
    }

    when {
        lazyLikedStories.loadState.refresh is LoadState.Loading || lazyLikedStories.loadState.append is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        lazyLikedStories.loadState.refresh is LoadState.Error || lazyLikedStories.loadState.append is LoadState.Error -> {
            val errorState = lazyLikedStories.loadState.refresh as LoadState.Error
            val errorMessage = errorState.error.localizedMessage ?: "An unknown error occurred"

            ErrorItem(
                message = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                onClickRetry = { lazyLikedStories.retry() }
            )
        }

        lazyLikedStories.itemCount == 0 -> {
            EmptyItemView(
                modifier = Modifier.fillMaxSize(),
                stringResource(R.string.empty_liked_story)
            )
        }

        else -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                gridGroups.forEach { (title, group) ->
                    header {
                        Text(
                            title,
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    bottom = 16.dp,
                                    start = 16.dp,
                                    end = 8.dp
                                )
                                .fillMaxWidth()
                        )
                    }

                    this.items(group) { story ->
                        StoryItem(story, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayLikedStoriesByAscend(
    viewModel: LikeListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val likedStories = viewModel.likedStoriesByAscend
    val lazyLikedStories = likedStories.collectAsLazyPagingItems()

    val gridGroups = remember { mutableStateListOf<GridGroup>() }

    LaunchedEffect(lazyLikedStories.itemSnapshotList) {
        updateGridGroups(lazyLikedStories, gridGroups)
    }

    when {
        lazyLikedStories.loadState.refresh is LoadState.Loading || lazyLikedStories.loadState.append is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        lazyLikedStories.loadState.refresh is LoadState.Error || lazyLikedStories.loadState.append is LoadState.Error -> {
            val errorState = lazyLikedStories.loadState.refresh as LoadState.Error
            val errorMessage = errorState.error.localizedMessage ?: "An unknown error occurred"

            ErrorItem(
                message = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                onClickRetry = { lazyLikedStories.retry() }
            )
        }

        lazyLikedStories.itemCount == 0 -> {
            EmptyItemView(
                modifier = Modifier.fillMaxSize(),
                stringResource(R.string.empty_liked_story)
            )
        }

        else -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                gridGroups.forEach { (title, group) ->
                    header {
                        Text(
                            title,
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    bottom = 16.dp,
                                    start = 16.dp,
                                    end = 8.dp
                                )
                                .fillMaxWidth()
                        )
                    }

                    this.items(group) { story ->
                        StoryItem(story, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayLikedStoriesByRandom(
    viewModel: LikeListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val likedStories = viewModel.likedStoriesByRandom
    val lazyLikedStories = likedStories.collectAsLazyPagingItems()

    when {
        lazyLikedStories.loadState.refresh is LoadState.Loading || lazyLikedStories.loadState.append is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        lazyLikedStories.loadState.refresh is LoadState.Error || lazyLikedStories.loadState.append is LoadState.Error -> {
            val errorState = lazyLikedStories.loadState.refresh as LoadState.Error
            val errorMessage = errorState.error.localizedMessage ?: "An unknown error occurred"

            ErrorItem(
                message = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                onClickRetry = { lazyLikedStories.retry() }
            )
        }

        lazyLikedStories.itemCount == 0 -> {
            EmptyItemView(
                modifier = Modifier.fillMaxSize(),
                stringResource(R.string.empty_liked_story)
            )
        }

        else -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                this.items(lazyLikedStories.itemCount) { index ->
                    val story = lazyLikedStories[index]
                    if (story != null) {
                        StoryItem(story, navController)
                    }
                }
            }
        }
    }
}

fun isContainsDateGroup(gridGroups: List<GridGroup>, storyDate: String): GridGroup? {
    return gridGroups.find { it.header == storyDate }
}

fun parseStoryDate(dateString: String?): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return try {
        dateString?.let { format.parse(it) }
    } catch (e: ParseException) {
        null
    }
}

fun formatStoryDate(date: Date?): String {
    val format = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    return date?.let { format.format(it) } ?: format.format(Date())
}

fun updateGridGroups(
    lazyLikedStories: LazyPagingItems<Story>,
    gridGroups: MutableList<GridGroup>,
) {
    for (index in 0 until lazyLikedStories.itemCount) {
        val story = lazyLikedStories[index] ?: return
        val storyDate = formatStoryDate(parseStoryDate(story.createdAt))

        // 그룹 리스트 중에 같은 그룹이 있는지 확인
        val existingGroup = isContainsDateGroup(gridGroups, storyDate)

        if (existingGroup != null) {
            //바로 전은 아니지만 기존의 그룹이 존재
            if (!existingGroup.stories.contains(story)) {
                existingGroup.stories.add(story)
            }
        } else {
            // 아예 새로운 그룹
            val newGroup = GridGroup(storyDate, mutableListOf(story))
            gridGroups.add(newGroup)
        }
    }
}
