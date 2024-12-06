package com.davidchura.sistema1232.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "mapas")
data class Mapa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val descripcion: String,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date()
)
