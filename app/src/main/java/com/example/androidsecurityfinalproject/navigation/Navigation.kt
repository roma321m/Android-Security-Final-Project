package com.example.androidsecurityfinalproject.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.androidsecurityfinalproject.navigation.Screens.Companion.MAIN_SCREEN
import com.example.androidsecurityfinalproject.navigation.destinations.decryptComposable
import com.example.androidsecurityfinalproject.navigation.destinations.encryptComposable
import com.example.androidsecurityfinalproject.navigation.destinations.mainComposable
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = MAIN_SCREEN
    ) {
        mainComposable(
            navigateToNextScreen = screen.main,
            mainViewModel = mainViewModel
        )
        encryptComposable(
            navigateToMainScreen = screen.encrypt,
            mainViewModel = mainViewModel
        )
        decryptComposable(
            navigateToMainScreen = screen.decrypt,
            mainViewModel = mainViewModel
        )
    }
}