package com.example.logos.usecase.word

import com.example.logos.data.repositoty.DatabaseRepository
import javax.inject.Inject

class FetchWordByGroupIdUseCase @Inject constructor(
	private val databaseRepository: DatabaseRepository
) {

	operator fun invoke(groupId: Long) = databaseRepository.getWordsByGroupId(groupId)
}