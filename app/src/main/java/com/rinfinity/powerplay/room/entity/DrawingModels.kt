package com.rinfinity.powerplay.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "drawings")
data class DrawingItem(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "imageName") val imageName: String,
    @ColumnInfo(name = "imageCreationTime") val imageCreationTime: String,
    @ColumnInfo(name = "imageMarkerCount") val imageMarkerCount: Int
)