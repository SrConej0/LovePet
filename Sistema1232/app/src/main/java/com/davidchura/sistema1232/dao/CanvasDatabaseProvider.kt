package com.davidchura.sistema1232.dao

import android.content.Context
import androidx.room.Room

object CanvasDatabaseProvider {
    private var instance: CanvasDatabase? = null

    fun getDatabase(context: Context): CanvasDatabase {
        return instance ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                CanvasDatabase::class.java,
                "canvas_database"
            ).build()
            instance = newInstance
            newInstance
        }
    }
}