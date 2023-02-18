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
import java.nio.ByteBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val MAX_TEXT_LENGTH = 99
    }

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
        if (newText.length <= MAX_TEXT_LENGTH) {
            toEncryptText = newText
        }
    }

    private fun updateToDecryptImageUri(uri: Uri?) {
        toDecryptImageUri = uri
    }

    private fun encrypt(context: Context) {
        val fileName = "image_${FileManager.getTimeStamp()}.jpg"
        val byteArray = toEncryptImageUri?.let { fileManager.uriToByteArray(it, context) } ?: return
        val fixedData = ByteBuffer.allocate(Int.SIZE_BYTES).putInt(toEncryptText.length).array()
        fileManager.saveFile(byteArray + toEncryptText.toByteArray() + fixedData, fileName)
        fileManager.shareFile(fileName, context)
    }

    private fun decrypt(context: Context) {
        val byteArray = toDecryptImageUri?.let { fileManager.uriToBytes(it, context) } ?: return
        val fixedData = 4
        val size = try {
            ByteBuffer.wrap(byteArray.sliceArray(IntRange(byteArray.size - fixedData, byteArray.size - 1))).int
        } catch (e: Exception) {
            0
        }
        val newByteArray = byteArray.sliceArray(IntRange(byteArray.size - size - fixedData, byteArray.size - 1 - fixedData))
        decryptedText = newByteArray.decodeToString()
    }
}