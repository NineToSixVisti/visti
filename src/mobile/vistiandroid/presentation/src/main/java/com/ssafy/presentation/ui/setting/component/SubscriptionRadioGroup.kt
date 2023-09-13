package com.ssafy.presentation.ui.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.domain.model.SubscriptionType
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.Pink80
import com.ssafy.presentation.ui.theme.SecondaryColor

@Composable
fun SubscriptionRadioGroup() {
    val textColor = if (isSystemInDarkTheme()) {
        LightBackgroundColor
    } else {
        DarkBackgroundColor
    }
    val backgroundColor = if (!isSystemInDarkTheme()) {
        LightBackgroundColor
    } else {
        DarkBackgroundColor
    }

    var selectedSubscriptionOption by remember {
        mutableStateOf(SubscriptionType.NORMAL)
    }
    val onSelectionChange = { type: SubscriptionType ->
        selectedSubscriptionOption = type
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(8.dp)
    ) {
        SubscriptionTypeItem("일반", "하루 5회 스토리 작성 가능\n하루 1회 nft 발행 가능", "월 0원",
            Modifier
                .clickable {
                    onSelectionChange(SubscriptionType.NORMAL)
                }
                .border(
                    2.dp, if (SubscriptionType.NORMAL == selectedSubscriptionOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }, shape = RoundedCornerShape(8.dp)
                ), Pink80, Black)
        SubscriptionTypeItem("정기 결제",
            "하루 10회 스토리 작성 가능\n하루 10회 nft 발행 가능",
            "월 900원",
            modifier = Modifier
                .clickable {
                    onSelectionChange(SubscriptionType.SUBSCRIBER)
                }
                .border(
                    2.dp, if (SubscriptionType.SUBSCRIBER == selectedSubscriptionOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }, shape = RoundedCornerShape(8.dp)
                ),
            backgroundColor, textColor)
        SubscriptionTypeItem("1회 결제",
            "하루 10회 스토리 작성 가능\n하루 10회 nft 발행 가능",
            "월 1,000원",
            modifier = Modifier
                .clickable {
                    onSelectionChange(SubscriptionType.ONETIME)
                }
                .border(
                    2.dp, if (SubscriptionType.ONETIME == selectedSubscriptionOption) {
                        SecondaryColor
                    } else {
                        backgroundColor
                    }, shape = RoundedCornerShape(8.dp)
                ),
            backgroundColor, textColor)
    }
}

@Composable
fun SubscriptionTypeItem(
    title: String,
    content: String,
    price: String,
    modifier: Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    Card(
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = modifier.background(backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp),
                        color = textColor
                    )
                    Text(
                        text = content,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                        color = textColor
                    )
                }
                Text(
                    text = price,
                    modifier = Modifier.padding(end = 24.dp),
                    color = textColor
                )
            }
        }
    }
}