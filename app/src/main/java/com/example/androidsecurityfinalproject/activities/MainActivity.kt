package com.example.androidsecurityfinalproject.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.androidsecurityfinalproject.manager.PermissionManager
import com.example.androidsecurityfinalproject.navigation.SetUpNavigation
import com.example.androidsecurityfinalproject.ui.theme.AndroidSecurityFinalProjectTheme
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var navController: NavHostController
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            // can use the results of the permission requests here.
        }
        val permissionManager = PermissionManager(applicationContext)

        Log.d(TAG, "onCreate")

        setContent {
            AndroidSecurityFinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberAnimatedNavController()
                    SetUpNavigation(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        permissionManager = permissionManager,
                        permissionLauncher = permissionLauncher
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidSecurityFinalProjectTheme {

    }
}