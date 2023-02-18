package com.example.androidsecurityfinalproject.manager

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.*


class FileManager {

    companion object {
        const val TAG = "FileManager"

        fun getTimeStamp(): String {
            val tsLong = System.currentTimeMillis() / 1000
            return tsLong.toString()
        }
    }

    private val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    private var isAvailable = false
    private var isWritable = false
    private var isReadable = false

    fun saveFile(data: ByteArray, name: String) {
        updateStorageAvailability()
        if (isWritable.not())
            return
        val file = File(folder, name)
        writeTextData(file, data)
    }

    fun shareFile(name: String, context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpeg"
        val uri = Uri.parse("$folder/$name")
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share image")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Share the image with the encrypted data inside")
        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }

    @SuppressLint("Recycle")
    fun uriToBytes(uri: Uri, context: Context): ByteArray? {
        return context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
    }

    fun uriToByteArray(uri: Uri, context: Context): ByteArray? {
        val drawable = uriToDrawable(uri, context) ?: return null
        val bitmap = drawableToBitmap(drawable) ?: return null
        return bitmapToByteArray(bitmap)
    }

    private fun uriToDrawable(uri: Uri, context: Context): Drawable? {
        return try {
            val inputStream: InputStream? = context.applicationContext.contentResolver.openInputStream(uri)
            Drawable.createFromStream(inputStream, uri.toString())
        } catch (e: FileNotFoundException) {
            null
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap: Bitmap
        if (drawable is BitmapDrawable) {
            Log.d(TAG, "drawableToBitmap: converting BitmapDrawable")
            bitmap = drawable.bitmap
        } else if (drawable.javaClass.simpleName == "AdaptiveIconDrawable") {
            Log.d(TAG, "drawableToBitmap: converting AdaptiveIconDrawable")
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        } else if (drawable is VectorDrawable) {
            Log.d(TAG, "drawableToBitmap: converting VectorDrawable")
            bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        } else {
            Log.d(TAG, "drawableToBitmap: creating bitmap")
            val w = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 1
            val h = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 1
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.draw(canvas)
        }
        return bitmap
    }

    private fun uriToBitmap(uri: Uri, context: Context): Bitmap? {
        try {
            val parcelFileDescriptor = context.applicationContext.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor ?: return null
            parcelFileDescriptor.close()
            return BitmapFactory.decodeFileDescriptor(fileDescriptor)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun writeTextData(file: File, data: ByteArray) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    Log.e(TAG, "${e.message}")
                }
            }
        }
    }

    private fun updateStorageAvailability() {
        when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> setReadWrite()
            Environment.MEDIA_MOUNTED_READ_ONLY -> setReadOnly()
            else -> setNotAvailable()
        }
    }

    private fun setReadWrite() {
        isAvailable = true
        isWritable = true
        isReadable = true
    }

    private fun setReadOnly() {
        isAvailable = true
        isWritable = false
        isReadable = true
    }

    private fun setNotAvailable() {
        isAvailable = false
        isWritable = false
        isReadable = false
    }
}