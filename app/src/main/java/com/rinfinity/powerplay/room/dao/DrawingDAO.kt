package com.rinfinity.powerplay.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rinfinity.powerplay.room.entity.DrawingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DrawingDAO  {

    @Query("SELECT * FROM drawings ORDER BY id DESC")
    fun getAllDrawingItems(): Flow<List<DrawingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: DrawingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(items: List<DrawingItem>)

}
