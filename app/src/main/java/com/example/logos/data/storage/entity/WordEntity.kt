package com.example.logos.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,
	val groupId: Long? = null,
	val word: String,
	val translations: List<String> = emptyList(),
	val tags: List<String> = emptyList(),
	val correctCount: Int = 0,
	val lastModifiedTimestamp: Long = System.currentTimeMillis()
)