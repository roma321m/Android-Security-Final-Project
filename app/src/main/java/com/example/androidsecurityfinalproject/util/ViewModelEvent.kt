package com.example.androidsecurityfinalproject.util

import android.net.Uri

sealed class ViewModelEvent {
    class UpdateToEncryptUri(val uri: Uri?): ViewModelEvent()
    class UpdateVisualUri(val uri: Uri?): ViewModelEvent()
}