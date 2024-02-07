package com.ssafy.presentation.ui.user.componet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ssafy.presentation.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOutLinedTextField(
    hint: String,
    text: String,
    keyboardType: KeyboardType,
    textState: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        value = text,
        onValueChange = textState,
        placeholder = {
            Text(
                text = hint,
                modifier = Modifier
                    .alpha(0.2f),
            )
        },
        singleLine = true,
        colors = outlinedTextFieldColors(
            cursorColor = Color(0x44E03C31),
            focusedBorderColor = PrimaryColor, unfocusedBorderColor = Color(0xFFEEEEEE)
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
    )
}
