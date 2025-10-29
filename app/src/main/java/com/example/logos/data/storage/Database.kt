package com.example.logos.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.logos.data.storage.dao.GroupDao
import com.example.logos.data.storage.dao.WordDao
import com.example.logos.data.storage.entity.GroupEntity
import com.example.logos.data.storage.entity.WordEntity

@Database(
	entities = [
		WordEntity::class,
		GroupEntity::class
	],
	version = 1,
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

	abstract fun getWordDao(): WordDao

	abstract fun getGroupDao(): GroupDao
}