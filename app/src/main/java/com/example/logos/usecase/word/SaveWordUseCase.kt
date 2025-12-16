package com.example.logos.usecase.word

import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.data.storage.entity.WordEntity
import javax.inject.Inject

class SaveWordUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {

    suspend operator fun invoke(newWord: String, meaning: String, groupId: Long?) =
        databaseRepository.saveWord(
            word = WordEntity(
                word = newWord,
                translations = listOf(meaning),
                groupId = groupId
            )
        )
}