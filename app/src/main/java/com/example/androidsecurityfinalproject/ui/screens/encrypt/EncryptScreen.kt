package com.example.androidsecurityfinalproject.ui.screens.encrypt

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
fun EncryptScreen(
    viewModel: MainViewModel
) {
    val toEncryptPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.handleEvent(ViewModelEvent.UpdateToEncryptUri(uri)) }
    )
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Encrypt", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.padding(20.dp))

        TextField(
            value = viewModel.toEncryptText,
            onValueChange = { newText ->
                viewModel.handleEvent(ViewModelEvent.UpdateToEncryptText(newText))
            },
            placeholder = {
                Text(text = "Text to encrypt")
            }
        )

        Spacer(modifier = Modifier.padding(20.dp))

        if (viewModel.toEncryptImageUri == null) {
            PickImage("Select image that will be encrypted:", toEncryptPhotoPickerLauncher)
        }

        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = viewModel.toEncryptImageUri,
            contentDescription = "encrypted image",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.padding(20.dp))

        if (viewModel.toEncryptImageUri != null && viewModel.toEncryptText.isNotBlank()) {
            Button(
                onClick = {
                    viewModel.handleEvent(ViewModelEvent.Encrypt(context))
                }
            ) {
                Text(text = "Encrypt & Share")
            }
        }
    }
}