package com.itis2019.lecturerecorder.di.module

import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.database.MarkDao
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.impl.FolderRepositoryImpl
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.repository.impl.LectureRepositoryImpl
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.repository.impl.MarkRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun repositoryModule() = Kodein.Module("repository"){
    bind<LectureRepository>() with singleton { provideLectureRepository(instance()) }
    bind<FolderRepository>() with singleton { provideFolderRepository(instance()) }
    bind<MarkRepository>() with singleton { provideMarkRepository(instance()) }
}

fun provideLectureRepository(lectureDao: LectureDao): LectureRepository =
    LectureRepositoryImpl(lectureDao)

fun provideFolderRepository(folderDao: FolderDao): FolderRepository =
    FolderRepositoryImpl(folderDao)

fun provideMarkRepository(markDao: MarkDao): MarkRepository =
    MarkRepositoryImpl(markDao)

