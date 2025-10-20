package com.example.logos.data.repositoty

import com.example.logos.data.storage.dao.WordDao
import com.example.logos.data.storage.entity.WordEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(
	private val wordDao: WordDao
) {

	fun getWordById(id: Long): Flow<WordEntity> = wordDao.getWordById(id)

	suspend fun saveWord(word: WordEntity) = wordDao.insert(word)

	suspend fun updateWord(word: WordEntity) = wordDao.update(
		item = word.copy(lastModifiedTimestamp = System.currentTimeMillis())
	)

	suspend fun getRandomWordId(excludeWord: WordEntity? = null) = wordDao.getRandomWordId(excludeWord?.id ?: -1)

	fun getRandomWords(excludeWord: WordEntity? = null) = wordDao.getRandomWords(excludeWord?.id ?: -1)
}