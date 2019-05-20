package com.itis2019.lecturerecorder.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder
import com.itis2019.lecturerecorder.repository.dbEntities.DbLecture
import com.itis2019.lecturerecorder.repository.dbEntities.DbMark
import com.itis2019.lecturerecorder.utils.DateConverter

@Database(entities = [DbLecture::class, DbFolder::class, DbMark::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lectureDao(): LectureDao
    abstract fun folderDao(): FolderDao
    abstract fun markDao(): MarkDao
}
