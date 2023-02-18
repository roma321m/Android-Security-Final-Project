package com.example.androidsecurityfinalproject.ui.screens.decrypt

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidsecurityfinalproject.ui.component.PickImage
import com.example.androidsecurityfinalproject.util.ViewModelEvent
import com.example.androidsecurityfinalproject.viewModels.MainViewModel

@Composable
fun DecryptScreen(
    viewModel: MainViewModel
) {
    val toDecryptPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.handleEvent(ViewModelEvent.UpdateToDecryptUri(uri)) }
    )
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Decrypt", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.padding(20.dp))

        if (viewModel.toDecryptImageUri == null) {
            PickImage("Select image that will be encrypted:", toDecryptPhotoPickerLauncher)
        }

        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = viewModel.toDecryptImageUri,
            contentDescription = "decrypted image",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.padding(20.dp))

        if (viewModel.toDecryptImageUri != null) {
            Button(
                onClick = {
                    viewModel.handleEvent(ViewModelEvent.Decrypt(context))
                }
            ) {
                Text(text = "Decrypt")
            }
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = viewModel.decryptedText)
    }
}