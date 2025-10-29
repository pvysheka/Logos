package com.example.logos.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.logos.data.storage.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

	@Insert
	suspend fun insert(item: WordEntity)

	@Update()
	suspend fun update(item: WordEntity)

	@Query("SELECT * FROM WordEntity WHERE tags LIKE '%' || :tag || '%'")
	suspend fun getWordsByTag(tag: String): List<WordEntity>

	@Transaction
	suspend fun getWordsByTags(tags: List<String>): List<WordEntity> {
		val result = mutableSetOf<WordEntity>()
		tags.forEach { tag ->
			result += getWordsByTag(tag)
		}
		return result.toList()
	}

	@Query("SELECT * FROM WordEntity WHERE id != :excludeId ORDER BY lastModifiedTimestamp ASC")
	fun getRandomWords(excludeId: Long): Flow<List<WordEntity>>

	@Query("SELECT * FROM WordEntity WHERE :id = id")
	fun getWordById(id: Long): Flow<WordEntity>

	@Query("SELECT * FROM WordEntity WHERE groupId = :groupId ORDER BY lastModifiedTimestamp ASC")
	fun getWordsByGroupId(groupId: Long): Flow<List<WordEntity>>

	@Query("SELECT * FROM WordEntity WHERE id != :excludeId ORDER BY RANDOM() LIMIT 1")
	suspend fun getRandomWordId(excludeId: Long): WordEntity

	@Delete
	fun delete(items: List<WordEntity>)
}
