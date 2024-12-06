package com.davidchura.sistema1232.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MapaDao {
    @Insert
    suspend fun insertMapa(mapa: Mapa)

    @Update
    suspend fun updateMapa(mapa: Mapa)

    @Delete
    suspend fun deleteMapa(mapa: Mapa)

    @Query("SELECT * FROM mapas")
    fun getAllMapas(): Flow<List<Mapa>>

    @Query("DELETE FROM mapas WHERE id = :mapaId")
    suspend fun deleteMapaById(mapaId: Int)
}
