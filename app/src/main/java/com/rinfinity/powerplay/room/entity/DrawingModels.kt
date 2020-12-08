package com.rinfinity.powerplay.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "drawings")
data class DrawingItem(
    @PrimaryKey var id: Long,
    @ColumnInfo(name = "imageUri") var imageUri: String,
    @ColumnInfo(name = "imageName") var imageName: String,
    @ColumnInfo(name = "imageCreationTime") var imageCreationTime: String,
    @ColumnInfo(name = "imageMarkerCount") var imageMarkerCount: Int
)