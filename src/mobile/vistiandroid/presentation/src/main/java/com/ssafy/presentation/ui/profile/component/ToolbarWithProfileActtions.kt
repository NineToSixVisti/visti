package com.ssafy.presentation.ui.profile.component

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.domain.model.LikeSortType
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor

@Composable
fun ToolbarWithProfileActions() {
    val iconColor = if (isSystemInDarkTheme()) {
        LightBackgroundColor
    } else {
        DarkBackgroundColor
    }
    val contextForToast = LocalContext.current.applicationContext

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                Toast.makeText(contextForToast, "User!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_rocket),
                contentDescription = LikeSortType.UP.name,
                tint = iconColor,
            )
        }

        IconButton(
            onClick = {
                Toast.makeText(contextForToast, "Menu!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = LikeSortType.UP.name,
                tint = iconColor,
            )
        }
    }
}