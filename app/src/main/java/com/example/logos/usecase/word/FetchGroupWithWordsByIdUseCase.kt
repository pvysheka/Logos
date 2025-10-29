package com.example.logos.usecase.word

import com.example.logos.data.repositoty.DatabaseRepository
import javax.inject.Inject

class FetchGroupWithWordsByIdUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {

    suspend operator fun invoke(groupId: Long) = databaseRepository.getGroupWithWordsById(groupId)
}