package com.ssafy.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadLottie(modifier: Modifier, animationCalendar: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = animationCalendar)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever // animate forever
    )
}
