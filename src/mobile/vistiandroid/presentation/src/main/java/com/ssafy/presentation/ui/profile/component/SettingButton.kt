package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.White

@Composable
fun SettingButton(
    imageId: Int,
    text: String,
    onClick: () -> Unit
) {
    val iconColor = if (isSystemInDarkTheme()) {
        White
    } else {
        Black
    }

    TextButton(onClick = onClick) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = imageId),
                contentDescription = text,
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, color = iconColor)
        }
    }
}