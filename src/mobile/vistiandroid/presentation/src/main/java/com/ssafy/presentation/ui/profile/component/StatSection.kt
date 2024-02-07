package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StatSection(modifier: Modifier = Modifier, storyCount: String, storyBoxCount: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(numberText = storyCount, text = "스토리")
        ProfileStat(numberText = storyBoxCount, text = "스토리 상자")
    }
}
