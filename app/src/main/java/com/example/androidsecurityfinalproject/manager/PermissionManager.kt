package com.example.androidsecurityfinalproject.manager

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.androidsecurityfinalproject.BuildConfig

class PermissionManager(
    private val context: Context
) {

    fun getPermissionList(): Array<String> {
        return getListOfPermissions().filterNot { isPermissionGranted(it) }.toTypedArray()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun manageExternalStorageLauncher() {
        context.startActivity(
            Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:${BuildConfig.APPLICATION_ID}")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun getListOfPermissions(): List<String> {
        val info: PackageInfo =
            context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS) ?: return emptyList()
        return info.requestedPermissions.toList()
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}