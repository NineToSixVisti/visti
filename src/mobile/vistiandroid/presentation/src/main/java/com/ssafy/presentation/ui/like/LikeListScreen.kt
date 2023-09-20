package com.ssafy.presentation.ui.like

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.domain.model.Story
import com.ssafy.presentation.ui.common.StoryItem
import com.ssafy.presentation.ui.like.component.GridGroup
import com.ssafy.presentation.ui.like.component.ToolbarWithLikeList
import com.ssafy.presentation.ui.like.component.header
import java.text.ParseException
import java.text.SimpleDateFormat
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
            val lazyLikedStories = likedStories.collectAsLazyPagingItems()

            val gridGroups = remember { mutableStateListOf<GridGroup>() }

            LaunchedEffect(lazyLikedStories.itemSnapshotList) {
                updateGridGroups(lazyLikedStories, gridGroups)
            }

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
                        StoryItem(story)
                    }
                }
            }
        }
    }
}

fun isNewDateGroup(prevStory: String, currentStory: String): Boolean {
    return prevStory != currentStory
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

fun updateGridGroups(lazyLikedStories: LazyPagingItems<Story>, gridGroups: MutableList<GridGroup>) {
    var previousDate = gridGroups.lastOrNull()?.header ?: ""

    for (index in 0 until lazyLikedStories.itemCount) {
        val story = lazyLikedStories[index] ?: return
        val storyDate = formatStoryDate(parseStoryDate(story.createdAt))

        if (isNewDateGroup(previousDate, storyDate)) {
            if (gridGroups.any { it.header == storyDate }) {
                gridGroups.find { it.header == storyDate }?.stories?.add(story)
            } else {
                val newGroup = GridGroup(storyDate, mutableListOf(story))
                gridGroups.add(newGroup)
            }
        } else {
            gridGroups.last().stories.add(story)
        }

        previousDate = storyDate
    }
}
