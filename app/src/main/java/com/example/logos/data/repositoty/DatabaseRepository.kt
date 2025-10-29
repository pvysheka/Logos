package com.example.logos.data.repositoty

import com.example.logos.data.storage.dao.GroupDao
import com.example.logos.data.storage.dao.WordDao
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.data.storage.entity.WordEntity
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(
	private val wordDao: WordDao,
	private val groupDao: GroupDao
) {

	// START: WordEntity

	fun getWordById(id: Long): Flow<WordEntity> = wordDao.getWordById(id)

	suspend fun saveWord(word: WordEntity) = wordDao.insert(word)

	suspend fun updateWord(word: WordEntity) = wordDao.update(
		item = word.copy(lastModifiedTimestamp = System.currentTimeMillis())
	)

	suspend fun getRandomWordId(excludeWord: WordEntity? = null) = wordDao.getRandomWordId(excludeWord?.id ?: -1)

	fun getRandomWords(excludeWord: WordEntity? = null) = wordDao.getRandomWords(excludeWord?.id ?: -1)

	fun getWordsByGroupId(groupId: Long) = wordDao.getWordsByGroupId(groupId)

	// END: WordEntity

	// START: GroupEntity

	suspend fun saveGroup(group: GroupEntity) = groupDao.insertGroup(group)

	fun getAllGroups(): Flow<List<GroupEntity>> = groupDao.getAllGroups()

	suspend fun getGroupWithWordsById(groupId: Long) = groupDao.getGroupWithWordsById(groupId)

	// END: GroupEntity
}