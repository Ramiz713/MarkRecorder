package com.itis2019.lecturerecorder.di.module

import android.app.Application
import androidx.room.Room
import com.itis2019.lecturerecorder.repository.database.AppDatabase
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.database.MarkDao
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
        )
            .build()

    @Provides
    @Singleton
    fun provideLectureDao(database: AppDatabase): LectureDao = database.lectureDao()

    @Provides
    @Singleton
    fun provideFolderDao(database: AppDatabase): FolderDao = database.folderDao()

    @Provides
    @Singleton
    fun provideMarkDao(database: AppDatabase): MarkDao = database.markDao()

    companion object {
        private const val DATABASE_NAME = "lecture_recorder.db"
    }
}
