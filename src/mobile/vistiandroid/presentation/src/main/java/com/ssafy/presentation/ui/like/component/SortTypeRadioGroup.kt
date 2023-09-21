package com.ssafy.presentation.ui.like.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.LikeSortType
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.SecondaryColor

@Composable
fun SortTypeRadioGroup(
    currentSortType: LikeSortType,
    onSortTypeChanged: (LikeSortType) -> Unit
) {
    val iconColor = if (isSystemInDarkTheme()) {
        LightBackgroundColor
    } else {
        DarkBackgroundColor
    }
    val backgroundColor = if (!isSystemInDarkTheme()) {
        LightBackgroundColor
    } else {
        DarkBackgroundColor
    }

    var selectedSortOption by remember {
        mutableStateOf(LikeSortType.DOWN)
    }

    val onSelectionChange = { type: LikeSortType ->
        onSortTypeChanged(type)
        selectedSortOption = type
    }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
                )
                .clickable {
                    onSelectionChange(LikeSortType.DOWN)
                }
                .background(
                    if (LikeSortType.DOWN == selectedSortOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }
                )
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = LikeSortType.DOWN.name,
                tint = iconColor,
            )
        }
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
                )
                .clickable {
                    onSelectionChange(LikeSortType.UP)
                }
                .background(
                    if (LikeSortType.UP == selectedSortOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }
                )
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_up),
                contentDescription = LikeSortType.UP.name,
                tint = iconColor,
            )
        }
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
                )
                .clickable {
                    onSelectionChange(LikeSortType.RANDOM)
                }
                .background(
                    if (LikeSortType.RANDOM == selectedSortOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }
                )
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_random),
                contentDescription = LikeSortType.RANDOM.name,
                tint = iconColor,
            )
        }
    }
}