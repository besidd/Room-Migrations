package com.example.roommigrations.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val username: String,
    @ColumnInfo(name = "createdAt", defaultValue = "0")
    val created: Long = System.currentTimeMillis()
)
