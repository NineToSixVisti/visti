package com.ssafy.presentation.ui.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.domain.model.LikeSortType
import com.ssafy.domain.model.UserType
import com.ssafy.presentation.R

@Composable
fun ProfileDescription(
    displayName: String, memberRole : UserType
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Medium,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        if(memberRole == UserType.SUBSCRIPTION) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = LikeSortType.UP.name,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }
}