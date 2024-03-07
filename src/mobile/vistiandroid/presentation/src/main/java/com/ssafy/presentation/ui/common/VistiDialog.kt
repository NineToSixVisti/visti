package com.ssafy.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.theme.White

@Composable
fun VistiDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    text: String,
    isDarkTheme: Boolean,
) {
    val background = if (!isDarkTheme) LightBackgroundColor else DarkBackgroundColor
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(background)
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val id = if (isDarkTheme) R.drawable.logo_white else R.drawable.logo_red
                val textColor = if (isDarkTheme) White else Black
                Image(
                    painter = painterResource(id = id),
                    contentDescription = text,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    alignment = Alignment.Center
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(24.dp),
                    color = textColor
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(stringResource(R.string.dialog_cancel), color = textColor)
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier
                            .weight(1f)
                            .background(PrimaryColor),
                    ) {
                        Text(stringResource(R.string.dialog_complete), color = White)
                    }
                }
            }
        }
    }
}
