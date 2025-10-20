package com.example.logos.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.logos.data.storage.dao.WordDao
import com.example.logos.data.storage.entity.WordEntity

@Database(
	entities = [
		WordEntity::class
	],
	version = 1,
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

	abstract fun getWordDao(): WordDao
}