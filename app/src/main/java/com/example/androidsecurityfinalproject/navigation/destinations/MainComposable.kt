package com.example.androidsecurityfinalproject.navigation.destinations

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.androidsecurityfinalproject.manager.PermissionManager
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.MAIN_SCREEN
import com.example.androidsecurityfinalproject.ui.screens.main.MainScreen
import com.example.androidsecurityfinalproject.util.Screen
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainComposable(
    navigateToNextScreen: (screen: Screen) -> Unit,
    mainViewModel: MainViewModel,
    permissionManager: PermissionManager,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {
    composable(
        route = MAIN_SCREEN
    ) {
        MainScreen(
            viewModel = mainViewModel,
            navigateToNextScreen = navigateToNextScreen,
            permissionManager = permissionManager,
            permissionLauncher = permissionLauncher
        )
    }
}