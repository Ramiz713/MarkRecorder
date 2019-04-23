package com.itis2019.lecturerecorder.di.module

import android.content.Context
import androidx.room.Room
import com.itis2019.lecturerecorder.repository.database.AppDatabase
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.database.MarkDao
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun roomModule() = Kodein.Module("room") {

    bind<AppDatabase>() with singleton { provideRoomDatabase(instance()) }
    bind<LectureDao>() with singleton { provideLectureDao(instance()) }
    bind<FolderDao>() with singleton { provideFolderDao(instance()) }
    bind<MarkDao>() with singleton { provideMarkDao(instance()) }
}

private fun provideRoomDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "lecture_recorder.db"
        ).build()

private fun provideLectureDao(database: AppDatabase): LectureDao = database.lectureDao()

private fun provideFolderDao(database: AppDatabase): FolderDao = database.folderDao()

private fun provideMarkDao(database: AppDatabase): MarkDao = database.markDao()
