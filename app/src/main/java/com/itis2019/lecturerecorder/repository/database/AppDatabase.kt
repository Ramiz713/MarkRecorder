package com.itis2019.lecturerecorder.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.model.Mark
import com.itis2019.lecturerecorder.utils.DateConverter

@Database(entities = [Lecture::class, Folder::class, Mark::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lectureDao(): LectureDao
    abstract fun folderDao(): FolderDao
    abstract fun markDao(): MarkDao
}
