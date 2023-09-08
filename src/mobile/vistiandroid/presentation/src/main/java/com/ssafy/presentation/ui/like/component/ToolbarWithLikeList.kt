package com.ssafy.presentation.ui.like.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithLikeList() {
    TopAppBar(
        title = {  },
        actions = {
            SortTypeRadioGroup()
        }
    )
}