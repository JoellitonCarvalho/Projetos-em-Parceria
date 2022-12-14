package com.sohid.brain23.database.di

import android.app.Application
import androidx.room.Room
import com.sohid.brain23.database.AppDatabase
import com.sohid.brain23.database.RepoDb
import com.sohid.brain23.database.RepoDbClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database Module
 * Define all the database-related classes that need to be provided in the scope of Application.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build()
  }

  @Provides
  @Singleton
  fun provideRepoDb(appDatabase: AppDatabase): RepoDb {
    return RepoDbClient(appDatabase.repositoryDao)
  }
}
