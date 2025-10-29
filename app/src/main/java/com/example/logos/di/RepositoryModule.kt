package com.example.logos.di

import com.example.logos.data.repositoty.DatabaseRepository
import com.example.logos.data.storage.dao.GroupDao
import com.example.logos.data.storage.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

	@Provides
	fun provideDatabaseRepository(wordDao: WordDao, groupDao: GroupDao) =
		DatabaseRepository(wordDao, groupDao)
}
