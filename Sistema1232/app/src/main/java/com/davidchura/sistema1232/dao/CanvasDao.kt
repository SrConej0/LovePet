package com.davidchura.sistema1232.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CanvasDao {
    @Insert
    suspend fun insertCanvas(canvas: Canvas)

    @Update
    suspend fun updateCanvas(canvas: Canvas)

    @Delete
    suspend fun deleteCanvas(canvas: Canvas)

    @Query("SELECT * FROM canvases")
    fun getAllCanvases(): Flow<List<Canvas>>

    @Query("DELETE FROM canvases WHERE id = :canvasId")
    suspend fun deleteCanvasById(canvasId: Int)
}