package com.rinfinity.powerplay.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

val CAMERA_PERMISSION_ARRAY = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)

fun isCameraPermissionAllowed(activity: Activity): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

}