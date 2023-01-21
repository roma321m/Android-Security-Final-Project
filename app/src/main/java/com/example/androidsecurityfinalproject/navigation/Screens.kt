package com.example.androidsecurityfinalproject.navigation

import androidx.navigation.NavController
import com.example.androidsecurityfinalproject.util.Screen

class Screens(navController: NavController) {

    companion object {
        const val ENCRYPT_SCREEN = "encrypt"
        const val DECRYPT_SCREEN = "decrypt"
        const val MAIN_SCREEN = "main"
    }

    val encrypt: () -> Unit = {
        navController.navigate(route = MAIN_SCREEN)
    }

    val decrypt: () -> Unit = {
        navController.navigate(route = MAIN_SCREEN)
    }

    val main: (Screen) -> Unit = { screen ->
        when(screen) {
            Screen.ENCRYPT -> {
                navController.navigate(route = ENCRYPT_SCREEN) {
                    popUpTo(MAIN_SCREEN) { inclusive = false }
                }
            }
            Screen.DECRYPT -> {
                navController.navigate(route = DECRYPT_SCREEN) {
                    popUpTo(MAIN_SCREEN) { inclusive = false }
                }
            }
        }
    }
}