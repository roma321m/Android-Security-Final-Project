package com.example.androidsecurityfinalproject.ui.screens.main

import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidsecurityfinalproject.manager.PermissionManager
import com.example.androidsecurityfinalproject.util.Screen
import com.example.androidsecurityfinalproject.viewModels.MainViewModel

@Composable
fun MainScreen(
    navigateToNextScreen: (screen: Screen) -> Unit,
    viewModel: MainViewModel,
    permissionManager: PermissionManager,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {
    if (permissionManager.getPermissionList().isEmpty()) {
        ShowPermissionMenu(
            permissionManager = permissionManager,
            permissionLauncher = permissionLauncher
        )
    } else {
        ShowMainMenu(
            navigateToNextScreen = navigateToNextScreen
        )
    }
}

@Composable
fun ShowPermissionMenu(
    permissionManager: PermissionManager,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This application require some permission to function",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Button(
            onClick = {
                permissionLauncher.launch(permissionManager.getPermissionList())
            }
        ) {
            Text(text = "Get Permissions")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Button(
                onClick = {
                    permissionManager.manageExternalStorageLauncher()
                }
            ) {
                Text(text = "Manage External Storage")
            }
        }
    }
}

@Composable
fun ShowMainMenu(
    navigateToNextScreen: (screen: Screen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, use this app to encrypt text inside a picture and share the picture with the encrypted data to others",
            style = MaterialTheme.typography.h5
        )

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