package com.example.androidsecurityfinalproject.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidsecurityfinalproject.manager.FileManager
import com.example.androidsecurityfinalproject.util.ViewModelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Arrays
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var toDecryptImageUri by mutableStateOf<Uri?>(null)
        private set

    var toEncryptImageUri by mutableStateOf<Uri?>(null)
        private set

    var toEncryptText by mutableStateOf("")
        private set

    var decryptedText by mutableStateOf("")
        private set

    private val fileManager = FileManager()

    fun handleEvent(event: ViewModelEvent) {
        when (event) {
            is ViewModelEvent.UpdateToDecryptUri -> updateToDecryptImageUri(event.uri)
            is ViewModelEvent.UpdateToEncryptUri -> updateToEncryptImageUri(event.uri)
            is ViewModelEvent.UpdateToEncryptText -> updateToEncryptText(event.text)
            is ViewModelEvent.Encrypt -> encrypt(event.context)
            is ViewModelEvent.Decrypt -> decrypt(event.context)
        }
    }

    private fun updateToEncryptImageUri(uri: Uri?) {
        toEncryptImageUri = uri
    }

    private fun updateToEncryptText(newText: String) {
        toEncryptText = newText
    }

    private fun updateToDecryptImageUri(uri: Uri?) {
        toDecryptImageUri = uri
    }

    private fun encrypt(context: Context) {
        val fileName = "image_${FileManager.getTimeStamp()}.jpg"
        val byteArray = toEncryptImageUri?.let { fileManager.uriToByteArray(it, context) } ?: return
        fileManager.saveFile(byteArray + toEncryptText.toByteArray(), fileName)
        fileManager.shareFile(fileName, context)
    }

    private fun decrypt(context: Context) {
        val byteArray = toDecryptImageUri?.let { fileManager.uriToBytes(it, context) } ?: return
        val size = toEncryptText.toByteArray().size
        val newByteArray = byteArray.sliceArray(IntRange(byteArray.size - size, byteArray.size -1))
        decryptedText = newByteArray.decodeToString()
    }
}