package com.ssafy.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.model.home.HomeStory
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.LoadLottie
import com.ssafy.presentation.ui.common.loadImage
import com.ssafy.presentation.ui.home.component.HomeStoryBoxItem
import com.ssafy.presentation.ui.home.component.HomeStoryItem
import com.ssafy.presentation.ui.theme.Black20
import com.ssafy.presentation.ui.theme.PrimaryColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()
    val homeStorySate = homeViewModel.homeStoryState.value
    val homeStoryBoxState = homeViewModel.homeStoryBoxState.value
    val memberInformation = homeViewModel.memberInformation.value.memberInformation


    when {
        homeStorySate.error.isNotBlank() -> {

        }

        homeStorySate.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {
                    Box(
                        modifier = Modifier

                            .fillMaxSize()
                            .height(540.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image_backgroud_sky),
                            contentDescription = "toolbar background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(state.toolbarState.progress)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(1 - state.toolbarState.progress)
                                .background(PrimaryColor)
                        )
                    }
                    HomeToolBar(state, homeViewModel)

                    Image(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(top = 10.dp, bottom = 15.dp)
                            .road(Alignment.Center, Alignment.BottomEnd)
                            .size(20.dp)
                            .alpha(1 - state.toolbarState.progress),
                        painter = painterResource(id = R.drawable.logo_white),
                        contentDescription = "home_logo"
                    )
                },
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(start = 20.dp, top = 20.dp)
                        .navigationBarsPadding()
                ) {
                    HomeContent(
                        homeStorySate.stories,
                        homeStoryBoxState.storyBoxList,
                        memberInformation
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    homeStoryList: List<HomeStory>,
    homeStoryBoxList: List<StoryBox>,
    memberInformation: Member
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {

        LoadLottie(
            Modifier
                .height(80.dp)
                .width(120.dp), R.raw.animation_calendar
        )
        HomeContentDes(memberInformation)
    }
    Text(
        text = "진행중인 기록",
        modifier = Modifier.padding(top = 20.dp, bottom = 5.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    if (homeStoryBoxList.isEmpty()) {
        LoadLottie(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp),
            animationCalendar = R.raw.animation_scan
        )
        Text(
            text = "진행중인 추억이 없어요.",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    } else {
        LazyRow {
            items(homeStoryBoxList) { homeStoryBox ->
                HomeStoryBoxItem(homeStoryBox)
            }
        }
    }

    Text(
        text = "과거의 기록",
        modifier = Modifier.padding(top = 20.dp, bottom = 5.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    if (homeStoryList.isEmpty()) {
        LoadLottie(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp),
            animationCalendar = R.raw.animation_scan
        )
        Text(
            text = "저장된 추억이 없어요.",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    } else {
        LazyRow {
            items(homeStoryList) { homeStory ->
                HomeStoryItem(homeStory)
            }
        }
    }
}


@Composable
fun HomeContentDes(memberInformation: Member) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${memberInformation.nickname}님", fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "의",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "보관된 추억은 ", fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = memberInformation.stories,
                fontSize = 18.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "개입니다!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 20.dp)
            )
        }

    }
}

@Composable
fun HomeToolBar(
    progress: CollapsingToolbarScaffoldState,
    homeViewModel: HomeViewModel,
) {

    val homeLastStoryBox = homeViewModel.homeLastStoryBoxState.value.storyBox
    val memberInformation = homeViewModel.memberInformation.value.memberInformation
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .statusBarsPadding()
            .alpha(progress.toolbarState.progress)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Image(
//                painter = painter,
//                contentDescription = "StoryBox",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .aspectRatio(3f / 2f)
//                    .fillMaxSize()
//            )

            Image(
                painter = loadImage(imageUrl = memberInformation.profilePath.toString()),
                contentDescription = "home profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .border(2.dp, Color.Black, CircleShape)
            )
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "home toolbar pencil"
                )
                Text(text = "x", color = Color.Black)
                Text(text = memberInformation.dailyStory.toString(), color = Color.Black)
            }
        }
        Text(
            text = homeViewModel.timerText.value,
            fontSize = 60.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )

        if (homeViewModel.homeLastStoryBoxState.value.storyBox.id == -1) {
            Text(
                text = "추억은 삶의 스케치북입니다.",
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "마감까지 남은 시간이에요!",
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(resId = R.raw.animation_diamond)
            )
            LottieAnimation(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(size = 200.dp)
                    .align(Alignment.CenterHorizontally),
                composition = composition,
                iterations = LottieConstants.IterateForever // animate forever
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .graphicsLayer {
                        shape = RoundedCornerShape(
                            40.dp
                        )
                        clip = true
                        shadowElevation = 10f
                    }
                    .background(Black20),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    modifier = Modifier
                        .alpha(1f)
                        .padding(20.dp, 16.dp, 0.dp, 16.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.logo_white),
                    contentDescription = "home_logo"
                )
                Text(
                    text = homeLastStoryBox.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "")
            }
        }
    }
}
