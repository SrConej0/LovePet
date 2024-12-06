package com.davidchura.sistema1232.dao

import android.content.Context
import androidx.room.Room

object MapaDatabaseProvider {
    private var instance: MapaDatabase? = null

    fun getDatabase(context: Context): MapaDatabase {
        return instance ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                MapaDatabase::class.java,
                "mapa_database"
            ).build()
            instance = newInstance
            newInstance
        }
    }
}
