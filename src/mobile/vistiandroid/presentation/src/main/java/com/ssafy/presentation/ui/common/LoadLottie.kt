package com.ssafy.presentation.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadLottie(height: Dp, width: Dp, animationCalendar: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = animationCalendar)
    )
    LottieAnimation(
        modifier = Modifier
            .height(height)
            .width(width),
        composition = composition,
        iterations = LottieConstants.IterateForever // animate forever
    )
}
