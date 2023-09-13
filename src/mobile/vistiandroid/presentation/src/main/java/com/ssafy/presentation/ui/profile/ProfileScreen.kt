package com.ssafy.presentation.ui.profile

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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssafy.domain.model.ImageWithText
import com.ssafy.domain.model.LikeSortType
import com.ssafy.presentation.R
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.ui.profile.component.PostTabView
import com.ssafy.presentation.ui.profile.component.ProfileSection
import com.ssafy.presentation.ui.profile.component.SettingButton
import com.ssafy.presentation.ui.profile.component.SettingSection
import com.ssafy.presentation.ui.profile.component.StoryBoxLazyColumn
import com.ssafy.presentation.ui.profile.component.StoryLazyVerticalGrid
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor
import com.ssafy.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.state.value

    val sheetState = rememberModalBottomSheetState()
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

                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        navController.navigate(route = SettingNav.Subscription.route)
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
                        SettingSection(navController = navController)
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
