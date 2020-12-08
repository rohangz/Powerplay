package com.rinfinity.powerplay.model

import android.net.Uri
import java.io.Serializable

data class DrawingListItemModel(
    val id: Long,
    val imageUri: Uri,
    val imageName: String,
    val imageCreationTime: String,
    val imageMarkerCount: Int
)