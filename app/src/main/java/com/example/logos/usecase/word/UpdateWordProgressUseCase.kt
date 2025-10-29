package com.example.logos.usecase.word

import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.data.storage.entity.WordEntity
import javax.inject.Inject

class UpdateWordProgressUseCase @Inject constructor(
	private val databaseRepository: DatabaseRepository
) {

	suspend operator fun invoke(word: WordEntity, value: Int) {
		val newValue = word.correctCount + value
		val updated = word.copy(correctCount = if (newValue < 0) 0 else newValue )
		databaseRepository.updateWord(updated)
	}
}