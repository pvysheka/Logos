package com.example.logos.usecase

import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.data.storage.entity.WordEntity
import javax.inject.Inject

class SaveWordUseCase @Inject constructor(
	private val databaseRepository: DatabaseRepository
) {

	suspend operator fun invoke(newWOrd: String, meaning: String) = databaseRepository.saveWord(
		word = WordEntity(word = newWOrd, translations = listOf(meaning))
	)
}