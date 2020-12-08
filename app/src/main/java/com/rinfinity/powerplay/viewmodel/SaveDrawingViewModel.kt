package com.rinfinity.powerplay.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.rinfinity.powerplay.utils.CONST_APP_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class SaveDrawingViewModel(app: Application) : AndroidViewModel(app) {

    lateinit var uri: Uri
    var fileName: String = ""



    val isFileNameValid: Boolean
        get() {
            if (fileName.isBlank() || fileName.contains(" ") || fileName.contains("."))
                return false
            return true
        }


    fun getDrawingCreationTime(): String {
        val dateFormat = SimpleDateFormat(CONST_APP_DATE_FORMAT, Locale.ENGLISH)
        return dateFormat.format(Calendar.getInstance().time).toString()
    }
}