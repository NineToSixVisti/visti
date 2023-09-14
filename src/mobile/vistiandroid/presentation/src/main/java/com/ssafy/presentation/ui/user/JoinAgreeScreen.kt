package com.ssafy.presentation.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.theme.PrimaryColor
import com.ssafy.presentation.ui.common.VistiButton

@Composable
fun JoinAgreeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "이용동의",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        val (joinAgreeAgeChecked, setChecked1) = remember { mutableStateOf(false) }
        val (joinAgreeServiceChecked, setChecked2) = remember { mutableStateOf(false) }
        val (joinAgreePersonalChecked, setChecked3) = remember { mutableStateOf(false) }
        val joinAgreeAllChecked =
            (joinAgreeAgeChecked && joinAgreeServiceChecked && joinAgreePersonalChecked)

        CheckBoxRow(
            text = "약관 전체 동의",
            value = joinAgreeAllChecked,
            onClick = {
                if (joinAgreeAllChecked) {
                    setChecked1(false)
                    setChecked2(false)
                    setChecked3(false)
                } else {
                    setChecked1(true)
                    setChecked2(true)
                    setChecked3(true)
                }
            },
        )

        CheckBoxRow(
            text = "만 14세 이상입니다",
            value = joinAgreeAgeChecked,
            onClick = { setChecked1(!joinAgreeAgeChecked) }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = joinAgreeServiceChecked,
                onCheckedChange = { setChecked2(!joinAgreeServiceChecked) })
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClickableText(
                    text = AnnotatedString("서비스 이용약관"), onClick = { }, modifier = Modifier,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                    contentDescription = "서비스 이용약관 확인"
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = joinAgreePersonalChecked,
                onCheckedChange = { setChecked3(!joinAgreePersonalChecked) })
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClickableText(
                    text = AnnotatedString("개인정보 처리방침"),
                    onClick = { },
                    modifier = Modifier,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                    contentDescription = "개인정보 처리방침 확인"
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(vertical = 15.dp)
        )
//        VistiButton("회원가입", PrimaryColor)
    }
}

@Composable
fun CheckBoxRow(text: String, value: Boolean, onClick: (Any) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = value, onCheckedChange = onClick)
        ClickableText(
            text = AnnotatedString(text),
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}