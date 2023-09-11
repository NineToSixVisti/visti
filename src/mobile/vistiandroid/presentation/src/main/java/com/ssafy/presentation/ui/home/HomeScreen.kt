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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.presentation.MainNav
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.MainBottomNavigationBar
import com.ssafy.presentation.ui.common.MainNavigationScreen
import com.ssafy.presentation.ui.theme.PrimaryColor
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    type: String? = null,
    articleId: String? = null
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val state = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
//            if (MainNav.isMainRoute(currentRoute)) {
//                MainBottomNavigationBar(
//                    navController = navController,
//                    currentRoute = currentRoute
//                )
//            }
        },
    ) {

        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(520.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_backgroud_sky),
                        contentDescription = "toolbar background",
                        contentScale = ContentScale.FillBounds,
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
                TopScrollView(state)

                Image(
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 0.dp, 16.dp)
                        .road(Alignment.Center, Alignment.BottomEnd)
                        .size(20.dp)
                        .alpha(1 - state.toolbarState.progress),
                    painter = painterResource(id = R.drawable.image_39),
                    contentDescription = "home_logo"
                )
            },
        ) {

            Column(modifier = Modifier.verticalScroll(scrollState)) {
                MainContent()
                MainContent()
                MainContent()
                MainContent()
            }
        }



        MainNavigationScreen(
            innerPaddings = it,
            navController = navController
        )
    }





}

@Composable
fun TopScrollView(progress: CollapsingToolbarScaffoldState) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .alpha(progress.toolbarState.progress)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_profile),
                contentDescription = "home profile image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .shadow(20.dp)
                    .border(2.dp, Color.Black, CircleShape)

            )
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "home toolbar pencil"
                )
                Text(text = "x", color = Color.Black)
                Text(text = "5", color = Color.Black)
            }
        }
        Text(
            text = "15:30:30",
            fontSize = 60.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.2f)
                .padding(horizontal = 10.dp), shape = RoundedCornerShape(60.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 0.dp, 16.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.image_39),
                    contentDescription = "home_logo"
                )
                Text(
                    text = "싸피 9기 1반",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "")
            }

        }
    }

}

@Composable
fun MainContent() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = R.raw.animation_calendar)
    )
    LottieAnimation(
        modifier = Modifier.size(size = 240.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever // animate forever
    )
}
