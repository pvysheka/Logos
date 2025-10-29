package com.example.logos.presentation.word.list

import com.example.logos.data.storage.entity.WordEntity

data class MainWordUIState(
	val words: List<WordEntity> = emptyList(),
	val word: WordEntity? = null,
)