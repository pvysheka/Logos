package com.example.logos.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String
)
