package com.example.logos.usecase

import com.example.logos.data.repositoty.DatabaseRepository
import javax.inject.Inject

class FetchAllWordsUseCase@Inject constructor(
	private val databaseRepository: DatabaseRepository
) {

	operator fun invoke() = databaseRepository.getRandomWords()
}