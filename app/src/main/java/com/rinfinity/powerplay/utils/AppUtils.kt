package com.rinfinity.powerplay.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val CONST_PACKAGE = "package"

fun moveToAppSettings(activity: Activity) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts(CONST_PACKAGE,activity.packageName, null)
    )
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    activity.startActivity(intent)
}

fun createUniqueFile(context: Context, extension: String): File? {
    return try {
        @SuppressLint("SimpleDateFormat") val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "img_" + timeStamp + "_"
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File.createTempFile(imageFileName, extension, storageDir)
    } catch (e: IOException) {
        null
    } catch (e: Exception) {
        null
    }
}
