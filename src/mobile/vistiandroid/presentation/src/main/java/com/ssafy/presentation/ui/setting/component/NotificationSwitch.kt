package com.ssafy.presentation.ui.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.presentation.ui.theme.PrimaryColor

@Composable
fun NotificationSwitch(
    title: String,
    content: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = content, fontSize = 10.sp, fontWeight = FontWeight.Thin)
        }
        Switch(
            checked = checked,
            onCheckedChange =
            onCheckedChange,
            colors = SwitchDefaults.colors(checkedTrackColor = PrimaryColor)
        )
    }
}