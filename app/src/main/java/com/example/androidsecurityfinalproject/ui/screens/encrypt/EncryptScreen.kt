package com.example.androidsecurityfinalproject.ui.screens.encrypt

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidsecurityfinalproject.ui.component.PickImage
import com.example.androidsecurityfinalproject.util.ViewModelEvent
import com.example.androidsecurityfinalproject.viewModels.MainViewModel
import com.example.androidsecurityfinalproject.viewModels.MainViewModel.Companion.MAX_TEXT_LENGTH

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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            value = viewModel.toEncryptText,
            onValueChange = { newText ->
                viewModel.handleEvent(ViewModelEvent.UpdateToEncryptText(newText))
            },
            placeholder = {
                Text(text = "Top secret :)")
            },
            label = {
                Text(text = "Text to encrypt")
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(end = 8.dp),
            text = "${viewModel.toEncryptText.length} / $MAX_TEXT_LENGTH",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            color = if (viewModel.toEncryptText.length >= MAX_TEXT_LENGTH) Color.Red else Color.LightGray
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