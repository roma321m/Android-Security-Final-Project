package com.example.androidsecurityfinalproject.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidsecurityfinalproject.util.Screen
import com.example.androidsecurityfinalproject.viewModels.MainViewModel

@Composable
fun MainScreen(
    navigateToNextScreen: (screen: Screen) -> Unit,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome, use this app to encrypt a picture inside a different one!")

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Button(
            onClick = {
                navigateToNextScreen(Screen.ENCRYPT)
            }
        ) {
            Text(text = "encrypt a picture into a link")
        }

        Button(
            onClick = {
                navigateToNextScreen(Screen.DECRYPT)
            }
        ) {
            Text(text = "decrypt a picture from a link")
        }
    }

}