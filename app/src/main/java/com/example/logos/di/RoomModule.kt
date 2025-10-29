package com.example.logos.di

import android.content.Context
import androidx.room.Room
import com.example.logos.data.storage.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DB_NAME = "logos_db"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(
                context = context,
                klass = Database::class.java,
                name = DB_NAME
            )
            .fallbackToDestructiveMigration() // TODO: only for development
            .build()

    @Provides
    fun provideWordDao(db: Database) = db.getWordDao()

    @Provides
    fun provideGroupDao(db: Database) = db.getGroupDao()
}
