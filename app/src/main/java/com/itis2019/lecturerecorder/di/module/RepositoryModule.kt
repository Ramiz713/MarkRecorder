package com.itis2019.lecturerecorder.di.module

import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.database.RecordDao
import com.itis2019.lecturerecorder.repository.impl.FolderRepositoryImpl
import com.itis2019.lecturerecorder.repository.impl.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLectureRepository(lectureDao: RecordDao): RecordRepository =
        RecordRepositoryImpl(lectureDao)

    @Provides
    @Singleton
    fun provideFolderRepository(folderDao: FolderDao): FolderRepository =
        FolderRepositoryImpl(folderDao)
}
