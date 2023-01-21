package com.example.androidsecurityfinalproject.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.DECRYPT_SCREEN
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.MAIN_SCREEN
import com.example.androidsecurityfinalproject.ui.screens.decrypt.DecryptScreen
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.decryptComposable(
    navigateToMainScreen: () -> Unit,
    mainViewModel: MainViewModel
) {
    composable(
        route = DECRYPT_SCREEN
    ) {
        DecryptScreen(
            navigateToMainScreen = navigateToMainScreen,
            viewModel = mainViewModel
        )
    }
}