package com.itis2019.lecturerecorder.di.module

import android.app.Application
import androidx.room.Room
import com.itis2019.lecturerecorder.repository.database.AppDatabase
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.database.RecordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideLectureDao(database: AppDatabase): RecordDao = database.lectureDao()

    @Provides
    @Singleton
    fun provideFolderDao(database: AppDatabase): FolderDao = database.folderDao()

    companion object {
        private const val DATABASE_NAME = "lecture_recorder.db"
    }
}
