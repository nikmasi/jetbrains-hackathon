package com.example.tasko.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "obaveza_table")
data class ObavezaRoom(
    @PrimaryKey val id: Int = 0,
    val isDone: Boolean,
    val name: String,
    val time: Long,
    val desc: String
)
