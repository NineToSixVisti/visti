package com.ssafy.presentation.ui.profile.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithProfile() {
    TopAppBar(
        title = { Text("noion0511@gmail.com", fontSize = 16.sp, fontWeight = FontWeight.Medium) },
        actions = {
            ToolbarWithProfileActions()
        }
    )
}