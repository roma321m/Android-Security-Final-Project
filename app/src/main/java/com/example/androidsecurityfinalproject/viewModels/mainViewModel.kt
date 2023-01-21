package com.example.androidsecurityfinalproject.viewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidsecurityfinalproject.util.ViewModelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var visualImageUri by mutableStateOf<Uri?>(null)
        private set

    var toEncryptImageUri by mutableStateOf<Uri?>(null)
        private set

    fun handleEvent(event: ViewModelEvent) {
        when(event) {
            is ViewModelEvent.UpdateVisualUri -> updateVisualImageUri(event.uri)
            is ViewModelEvent.UpdateToEncryptUri -> updateToEncryptImageUri(event.uri)
        }
    }

    private fun updateToEncryptImageUri(uri: Uri?) {
        toEncryptImageUri = uri
    }

    private fun updateVisualImageUri(uri: Uri?) {
        visualImageUri = uri
    }

    private fun encrypt() {

    }

    private fun decrypt() {

    }
}