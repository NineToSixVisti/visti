package com.ssafy.presentation.ui.setting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ssafy.presentation.R
import com.ssafy.presentation.ui.setting.component.BackToolbar
import com.ssafy.presentation.ui.setting.component.DetailSettingButton
import com.ssafy.presentation.ui.theme.Black
import com.ssafy.presentation.ui.theme.White


@Composable
fun InformationScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            BackToolbar(text = stringResource(R.string.information)) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            val colorState = if (isSystemInDarkTheme()) {
                White
            } else {
                Black
            }

            DetailSettingButton(stringResource(R.string.personal_information_policy), colorState) {

            }
            DetailSettingButton(stringResource(R.string.opnesource_library), colorState) {

            }
        }
    }
}
