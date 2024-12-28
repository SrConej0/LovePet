package com.davidchura.sistema1232.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Canvas::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CanvasDatabase : RoomDatabase() {
    abstract fun canvasDao(): CanvasDao
}