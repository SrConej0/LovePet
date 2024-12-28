package com.davidchura.sistema1232.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "canvases")
data class Canvas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val numero1: Int,
    val numero2: Int,
    val numero3: Int,
    val numero4: Int,
    val numero5: Int,
    val numero6: Int,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date()
)