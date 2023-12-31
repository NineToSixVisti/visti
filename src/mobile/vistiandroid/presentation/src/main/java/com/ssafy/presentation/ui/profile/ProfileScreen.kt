package com.ssafy.presentation.ui.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.domain.model.ImageWithText
import com.ssafy.domain.model.LikeSortType
import com.ssafy.presentation.R
import com.ssafy.presentation.SettingNav
import com.ssafy.presentation.ui.profile.component.PostTabView
import com.ssafy.presentation.ui.profile.component.ProfileSection
import com.ssafy.presentation.ui.profile.component.SettingSection
import com.ssafy.presentation.ui.profile.component.StoryBoxLazyColumn
import com.ssafy.presentation.ui.profile.component.StoryLazyVerticalGrid
import com.ssafy.presentation.ui.theme.DarkBackgroundColor
import com.ssafy.presentation.ui.theme.LightBackgroundColor

private const val TAG = "ProfileScreen"

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController) {
    Log.d(TAG, "ProfileScreen hilt viewModel id: ${viewModel.hashCode()}")

    val myStories = viewModel.myStories.collectAsLazyPagingItems()
    val lazyPagingItems = viewModel.myStoryBoxes.collectAsLazyPagingItems()

    val memberInformationState = viewModel.memberInformation.value
    val memberInformation = memberInformationState.memberInformation

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        memberInformation.email,
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
            ProfileSection(
                memberInformation.nickname,
                memberInformation.role,
                memberInformation.profilePath,
                memberInformation.stories,
                memberInformation.storyBoxes
            )
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
                0 -> StoryLazyVerticalGrid(
                    myStories,
                    memberInformation.stories,
                    navController,
                    viewModel
                )

                1 -> StoryBoxLazyColumn(
                    lazyPagingItems,
                    memberInformation.storyBoxes,
                    navController
                )
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
