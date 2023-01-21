package com.example.androidsecurityfinalproject.ui.screens.decrypt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidsecurityfinalproject.viewModels.MainViewModel

@Composable
fun DecryptScreen(
    navigateToMainScreen: () -> Unit,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Decrypt")

        Button(
            onClick = {
                navigateToMainScreen()
            }
        ) {
            Text(text = "back")
        }
    }
}