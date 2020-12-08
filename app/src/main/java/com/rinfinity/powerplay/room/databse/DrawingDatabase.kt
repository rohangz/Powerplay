package com.rinfinity.powerplay.room.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rinfinity.powerplay.room.dao.DrawingDAO
import com.rinfinity.powerplay.room.entity.DrawingItem

@Database(entities = [DrawingItem::class], version = 1)
abstract class DrawingDatabase : RoomDatabase() {

    abstract fun getDrawingDAO(): DrawingDAO
}