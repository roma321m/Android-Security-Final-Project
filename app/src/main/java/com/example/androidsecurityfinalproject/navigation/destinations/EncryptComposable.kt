package com.example.androidsecurityfinalproject.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.ENCRYPT_SCREEN
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.MAIN_SCREEN
import com.example.androidsecurityfinalproject.ui.screens.encrypt.EncryptScreen
import com.example.androidsecurityfinalproject.ui.screens.main.MainScreen
import com.example.androidsecurityfinalproject.util.Screen
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.encryptComposable(
    mainViewModel: MainViewModel
) {
    composable(
        route = ENCRYPT_SCREEN
    ) {
        EncryptScreen(
            viewModel = mainViewModel
        )
    }
}