package com.itis2019.markrecorder.di.module

import com.itis2019.markrecorder.repository.FolderRepository
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.repository.database.FolderDao
import com.itis2019.markrecorder.repository.database.RecordDao
import com.itis2019.markrecorder.repository.impl.FolderRepositoryImpl
import com.itis2019.markrecorder.repository.impl.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLectureRepository(recordDao: RecordDao): RecordRepository =
        RecordRepositoryImpl(recordDao)

    @Provides
    @Singleton
    fun provideFolderRepository(folderDao: FolderDao): FolderRepository =
        FolderRepositoryImpl(folderDao)
}
