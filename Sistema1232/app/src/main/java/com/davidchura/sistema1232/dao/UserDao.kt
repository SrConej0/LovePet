package com.davidchura.sistema1232.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
     fun getAllUsers(): Flow<List<User>>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)


}