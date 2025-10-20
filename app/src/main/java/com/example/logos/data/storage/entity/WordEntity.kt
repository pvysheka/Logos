package com.example.logos.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,
	val word: String,
	val themes: List<String> = emptyList(),
	val translations: List<String> = emptyList(),
	val correctCount: Int = 0,
	val lastModifiedTimestamp: Long = System.currentTimeMillis()
)