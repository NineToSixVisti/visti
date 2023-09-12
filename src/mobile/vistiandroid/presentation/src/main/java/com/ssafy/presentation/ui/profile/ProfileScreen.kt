package com.ssafy.presentation.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.domain.model.ImageWithText
import com.ssafy.domain.model.LikeSortType
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.common.StoryBoxItem
import com.ssafy.presentation.ui.profile.component.StoryBoxLazyColumn
import com.ssafy.presentation.ui.profile.component.StoryLazyVerticalGrid
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.VistiAndroidTheme
import com.ssafy.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    when {
        state.error.isNotBlank() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error)
            }
        }

        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "noion0511@gmail.com",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        actions = {
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
                                        Toast.makeText(contextForToast, "User!", Toast.LENGTH_SHORT)
                                            .show()
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
                                        showBottomSheet = true
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
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    ProfileSection()
                    Spacer(modifier = Modifier.height(15.dp))
                    PostTabView(
                        imageWithText = listOf(
                            ImageWithText(
                                image = painterResource(id = R.drawable.ic_story),
                                text = "Story"
                            ),
                            ImageWithText(
                                image = painterResource(id = R.drawable.ic_box),
                                text = "Box"
                            ),
                            ImageWithText(
                                image = painterResource(id = R.drawable.ic_token),
                                text = "NFT"
                            )
                        )
                    ) {
                        selectedTabIndex = it
                    }
                    when (selectedTabIndex) {
                        0 -> StoryLazyVerticalGrid(state.stories)
                        1 -> StoryBoxLazyColumn(state.stories)
                    }
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        SettingSection()
                        Spacer(
                            Modifier.windowInsetsBottomHeight(
                                WindowInsets.navigationBars
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileSection() {
    Column(Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            ImageSection(
                image = painterResource(id = R.drawable.ic_profile),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f))
        }
        Spacer(modifier = Modifier.height(10.dp))
        ProfileDescription(
            displayName = "ohhuiju"
        )
    }
}

@Composable
fun ImageSection(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(Color.LightGray)
            .padding(10.dp)

    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(numberText = "1,000", text = "스토리")
        ProfileStat(numberText = "34", text = "스토리 상자")
    }
}

@Composable
fun SettingSection(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        SettingButton(imageId = R.drawable.ic_notification, text = "알림 설정")
        SettingButton(imageId = R.drawable.ic_info, text = "정보")
        SettingButton(imageId = R.drawable.ic_person, text = "계정")
    }
}

@Composable
fun ProfileDescription(
    displayName: String
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
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = LikeSortType.UP.name,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}


@Composable
fun SettingButton(
    imageId: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = imageId),
            contentDescription = text
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text)
    }
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithText: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val iconColor = if (isSystemInDarkTheme()) {
        White
    } else {
        Black
    }

    val inactiveColor = Color(0xFF777777)

    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = iconColor,
        modifier = modifier
    ) {
        imageWithText.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = iconColor,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = item.image,
                    contentDescription = item.text,
                    tint = if (selectedTabIndex == index) iconColor else inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}