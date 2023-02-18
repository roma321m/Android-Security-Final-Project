package com.example.androidsecurityfinalproject.util

import android.content.Context
import android.net.Uri

sealed class ViewModelEvent {
    class UpdateToEncryptUri(val uri: Uri?): ViewModelEvent()
    class UpdateToDecryptUri(val uri: Uri?): ViewModelEvent()
    class UpdateToEncryptText(val text: String): ViewModelEvent()
    class Encrypt(val context: Context): ViewModelEvent()
    class Decrypt(val context: Context): ViewModelEvent()
}