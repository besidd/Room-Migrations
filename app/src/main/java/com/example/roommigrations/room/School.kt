package com.example.roommigrations.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class School(
    @PrimaryKey(autoGenerate = false)
    val name: String
)
