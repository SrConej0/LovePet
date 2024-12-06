package com.davidchura.sistema1232.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Mapa::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MapaDatabase : RoomDatabase() {
    abstract fun mapaDao(): MapaDao
}
