package com.example.tasko.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ObavezaDao {
    @Query("SELECT * FROM obaveza_table ORDER BY name ASC")
    fun getObaveze(): Flow<List<ObavezaRoom>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obaveza: ObavezaRoom)

    @Query("DELETE FROM obaveza_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM obaveza_table WHERE id=:id")
    fun dohvatiObavezu(id:Int): ObavezaRoom?

    @Query("SELECT * FROM obaveza_table WHERE isDone = 0 AND time <= :now")
    suspend fun getRelevantObaveze(now: Long): List<ObavezaRoom>
}
